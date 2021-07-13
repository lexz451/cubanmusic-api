<?php
declare(strict_types=1);

use App\Controllers\LoginController;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Slim\App;
use Slim\Interfaces\RouteCollectorProxyInterface as Group;

return function (App $app) {
    $app->options('/{routes:.*}', function (Request $request, Response $response) {
        // CORS Pre-Flight OPTIONS Request Handler
        return $response;
    });

    $app->get('/', function (Request $request, Response $response) {
        $username = $_ENV["DB_USERNAME"];
        $response->getBody()->write("Welcome " . $username);
        return $response;
    });

    // CubanMusic Api v1.0
    $app->group('/api/v1', function (Group $group) {
        $group->post('/login', [LoginController::class, 'login']);
    });
};
