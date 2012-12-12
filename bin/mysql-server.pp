include install_mysql

$BIN = $install_mysql::BIN
$SCHEMA = $install_mysql::SCHEMA

#linux user data
$user = $install_mysql::user

# standard mysql root user
$mysqlRootUser = $install_mysql::mysqlRootUser
$mysqlRootPass = $install_mysql::mysqlRootPass

#app specific data
$appUser = $install_mysql::appUser
$appPass = $install_mysql::appPass
$dbName = $install_mysql::dbName

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

notify {"blah" :
	message => "blahblah  $SCHEMA"
}

file{"database-schema":
  ensure => present,
  path => "$SCHEMA"
}


Notify ["blah"] -> Exec["set-mysql-password"] -> Exec["create-database"] -> Exec["import-schema"] -> Exec["create-application-user"] -> Exec["add-permission-to-user"]

