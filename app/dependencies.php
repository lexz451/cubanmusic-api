<?php

declare(strict_types=1);

use Cache\Adapter\Apc\ApcCachePool;
use Cache\Adapter\PHPArray\ArrayCachePool;
use Cache\Bridge\Doctrine\DoctrineCacheBridge;
use DI\ContainerBuilder;
use Doctrine\ORM\EntityManager;
use Doctrine\ORM\Tools\Setup;
use Monolog\Handler\StreamHandler;
use Monolog\Logger;
use Monolog\Processor\UidProcessor;
use Psr\Container\ContainerInterface;
use Psr\Log\LoggerInterface;
use Selective\Validation\Factory\CakeValidationFactory;

return function (ContainerBuilder $containerBuilder) {
    $containerBuilder->addDefinitions([
        LoggerInterface::class => function (ContainerInterface $c) {
            $settings = $c->get('settings');

            $loggerSettings = $settings['logger'];
            $logger = new Logger($loggerSettings['name']);

            $processor = new UidProcessor();
            $logger->pushProcessor($processor);

            $handler = new StreamHandler($loggerSettings['path'], $loggerSettings['level']);
            $logger->pushHandler($handler);

            return $logger;
        },
        EntityManager::class => function (ContainerInterface $c) {
            $settings = $c->get('settings');
            $config = Setup::createAnnotationMetadataConfiguration(
                $settings['doctrine']['metadata_dirs'],
                $settings['doctrine']['dev_mode']
            );

            if ($_ENV['APP_DEBUG']) {
                $pool = new ArrayCachePool();
            } else {
                $pool = new ApcCachePool();
            }

            $config->setMetadataCache($pool);

            $cache = new DoctrineCacheBridge($pool);
            $config->setQueryCacheImpl($cache);
            
            return EntityManager::create($settings['doctrine']['connection'], $config);
        },
        CakeValidationFactory::class => function () {
            return new CakeValidationFactory();
        }
    ]);
};
