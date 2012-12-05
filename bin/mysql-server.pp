$BIN = "/home/justin/workspace/DBunitTest/DBunitTest/bin"
$SCHEMA = "/home/justin/workspace/DBunitTest/DBunitTest/src/test/resources/WorldDDL.sql"

#linux user data
$user = "justin"

# standard mysql root user
$mysqlRootUser = "root"
$mysqlRootPass = "redhat"

#app specific data
$appUser = "dbunit"
$appPass = "redhat"
$dbName = "blahblah"

file{"database-schema":
  ensure => present,
  path => "$SCHEMA"
}

package {"mysql-server":
  ensure => installed
}

package {"mysql":
  ensure => installed
}

service { "mysql-daemon":
  require => Package["mysql-server"],
  ensure => running,
  name => "mysqld"
}

exec { "set-mysql-password":
    unless => "mysqladmin -u $mysqlRootUser -p $mysqlRootPass status",
    path => ["/bin", "/usr/bin"],
    command => "mysqladmin -uroot password redhat",
    require => Service["mysqld"]
}

exec { "create-database" :
    path => ["/bin", "/usr/bin"],
    command => "mysql -u $mysqlRootUser -p$mysqlRootPass -e \"create database $dbName;\"",
    require => Service["mysqld"]
}

exec { "import-schema" :
    path => ["/bin", "/usr/bin"],
    command => "mysql -u $mysqlRootUser -p$mysqlRootPass -h localhost $dbName < $SCHEMA",
    require => Service["mysqld"]
}

exec { "create-application-user" :
    path => ["/bin", "/usr/bin"],
    command => "mysql -u $mysqlRootUser -p$mysqlRootPass -e  \"create user '$appUser'@'localhost' identified by '$appPass';\"",
    require => Service["mysqld"]
}



exec { "add-permission-to-user" :
    path => ["/bin", "/usr/bin"],
    command => "mysql -u $mysqlRootUser -p$mysqlRootPass -e  \"grant all privileges on $dbName.* to '$appUser'@'localhost';\"",
    require => Service["mysqld"]
}


Exec["set-mysql-password"] -> Exec["create-database"] -> Exec["import-schema"] -> Exec["create-application-user"] -> Exec["add-permission-to-user"]
