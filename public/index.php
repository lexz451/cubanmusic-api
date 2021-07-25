<?php

declare(strict_types=1);

use DI\ContainerBuilder;
use Dotenv\Dotenv;
use Slim\Factory\AppFactory;

require_once __DIR__ . '/../vendor/autoload.php';

// Instantiate PHP-DI ContainerBuilder
$containerBuilder = new ContainerBuilder();


$env = Dotenv::createImmutable(__DIR__ . '/../');
$env->load();

if ($_ENV['APP_DEBUG']) { // Should be set to true in production
	$containerBuilder->enableCompilation(__DIR__ . '/../var/cache');
}

// Set up settings
$settings = require_once __DIR__ . '/../app/settings.php';
$settings($containerBuilder);

// Set up dependencies
$dependencies = require_once __DIR__ . '/../app/dependencies.php';
$dependencies($containerBuilder);

// Set up repositories
$repositories = require_once __DIR__ . '/../app/repositories.php';
$repositories($containerBuilder);

// Set up services
$services = require_once __DIR__ . '/../app/services.php';
$services($containerBuilder);

// Build PHP-DI Container instance
$container = $containerBuilder->build();

// Instantiate the app
AppFactory::setContainer($container);
$app = AppFactory::create();

$app->getContainer()->get("db"); // Fix: Eloquent ORM on Slim initialization

// Register middleware
$middleware = require_once __DIR__ . '/../app/middleware.php';
$middleware($app);

// Register routes
$routes = require_once __DIR__ . '/../app/routes.php';
$routes($app);

$app->run();
