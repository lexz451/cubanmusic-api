<?php

namespace App\Controllers;

use App\Repositories\UserRepository;
use Cake\Validation\Validator;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;
use Selective\Validation\Exception\ValidationException;

class LoginController extends BaseController {

    public function login(ServerRequestInterface $request, ResponseInterface $response) {

        $data = (array)$request->getParsedBody();
        $validator = $this->createValidation();
        $validationResult = $this->validatorFactory->createValidationResult(
            $validator->validate($data)
        );

        if ($validationResult->fails()) {
            throw new ValidationException('Invalid request', $validationResult);
        }

        // Validate request
        $message = [
            'token' => 'xx'
        ];

        return $this->jsonResponse($response, 'success', $message, 200);
    }

    private function createValidation(): Validator {
        $validator = $this->validatorFactory->createValidator();
        $validator
            ->notEmptyString('email', 'Email required.')
            ->notEmptyString('password', 'Password required.')
            ->requirePresence('email', 'Email required.')
            ->requirePresence('password', 'Password required.')
            ->email('email', false, 'Invalid email');
        return $validator;
    }
}