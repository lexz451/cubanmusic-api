<?php
declare(strict_types=1);
define('APP_ROOT', __DIR__ . '/../');

use DI\ContainerBuilder;
use Monolog\Logger;

return function (ContainerBuilder $containerBuilder) {

    // Global Settings Object
    $containerBuilder->addDefinitions([
        'settings' => function () {
            return [
                'debug' => $_ENV['APP_DEBUG'],
                'logger' => [
                    'name' => $_ENV['APP_NAME'],
                    'path' => __DIR__ . '/../logs/app.log',
                    'level' => Logger::DEBUG,
                ],
                /*'db' => [
                    'driver' => 'mysql',
                    'host' => $_ENV['DB_HOST'],
                    'port' => $_ENV['DB_PORT'],
                    'database' => $_ENV['DB_NAME'],
                    'username' => $_ENV['DB_USERNAME'],
                    'password' => $_ENV['DB_PASSWORD']
                ],*/
                'doctrine' => [
                    'dev_mode' => $_ENV['APP_DEBUG'],
                    'cache_dir' => APP_ROOT . '/var/doctrine',
                    'metadata_dirs' => [APP_ROOT . '/src/Models'],
                    'connection' => [
                        'driver' => 'pdo_mysql',
                        'host' => $_ENV['DB_HOST'],
                        'port' => $_ENV['DB_PORT'],
                        'dbname' => $_ENV['DB_NAME'],
                        'user' => $_ENV['DB_USERNAME'],
                        'password' => $_ENV['DB_PASSWORD'],
                        'charset' => 'utf-8'
                    ]
                ]
            ];
        }
    ]);
};
