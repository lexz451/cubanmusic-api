<?php
declare(strict_types=1);

use App\Application\Middleware\SessionMiddleware;
use Selective\Validation\Encoder\JsonEncoder;
use Selective\Validation\Middleware\ValidationExceptionMiddleware;
use Selective\Validation\Transformer\ErrorDetailsResultTransformer;
use Slim\App;
use Zeuxisoo\Whoops\Slim\WhoopsMiddleware;

return function (App $app) {
    $settings = $app->getContainer()->get('settings');
    $debug = $settings['debug'];
    if ($debug) {
        $app->add(new WhoopsMiddleware());
    }
    $app->addBodyParsingMiddleware();
    $app->add(
        new ValidationExceptionMiddleware(
            $app->getResponseFactory(),
            new ErrorDetailsResultTransformer(),
            new JsonEncoder()
        )
        );
};
