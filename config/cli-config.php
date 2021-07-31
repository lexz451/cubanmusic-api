<?php

use DI\ContainerBuilder;
use Doctrine\ORM\EntityManager;
use Doctrine\ORM\Tools\Console\ConsoleRunner;
use Dotenv\Dotenv;

$containerBuilder = new ContainerBuilder();

$env = Dotenv::createImmutable(__DIR__ . '/../');
$env->load();

$settings = require_once __DIR__ . '/../app/settings.php';
$settings($containerBuilder);

$dependencies = require_once __DIR__ . '/../app/dependencies.php';
$dependencies($containerBuilder);

$container = $containerBuilder->build();

$em = $container->get(EntityManager::class);

return ConsoleRunner::createHelperSet($em);