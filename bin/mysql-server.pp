$BIN = "/home/michael/workspace/puppet/dbunitpoc/bin"
$SCHEMA = "/home/michael/workspace/puppet/DBunitTest/src/test/resources/WorldDDL.sql"

#linux user data
$user = "michael"

# standard mysql root user
$mysqlRootUser = "root"
$mysqlRootPass = "redhat"

#app specific data
$appUser = "dbunit"
$appPass = "redhat"
$dbName = "test"

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
    command => "mysql -u $mysqlRootUser -p$mysqlRootPass -e \"create database world;\"",
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

Exec["set-mysql-password"] -> Exec["create-database"] -> Exec["import-schema"] -> Exec["create-application-user"]