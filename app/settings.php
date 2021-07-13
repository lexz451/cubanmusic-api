<?php
declare(strict_types=1);


use DI\ContainerBuilder;
use Monolog\Logger;

return function (ContainerBuilder $containerBuilder) {

    // Global Settings Object
    $containerBuilder->addDefinitions([
        'settings' => function () {
            return [
                'debug' => true,
                'logger' => [
                    'name' => $_ENV['APP_NAME'],
                    'path' => __DIR__ . '/../logs/app.log',
                    'level' => Logger::DEBUG,
                ],
                'db' => [
                    'driver' => 'mysql',
                    'host' => $_ENV['DB_HOST'],
                    'port' => $_ENV['DB_PORT'],
                    'database' => $_ENV['DB_NAME'],
                    'username' => $_ENV['DB_USERNAME'],
                    'password' => $_ENV['DB_PASSWORD']
                ]
            ];
        }
    ]);
};
