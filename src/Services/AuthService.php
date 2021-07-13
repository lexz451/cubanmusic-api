<?php

namespace App\Services;

use DateTime;
use Firebase\JWT\JWT;
use User;

class AuthService {

    public function login($username, $password) {
        $user = $this->userRepository->findByEmailAndPassword($username, $password);
        if ($user) {
            $token = $this->generateToken($user);
            return $token;
        }
        return false;
    }

    public function generateToken(User $user) {
        $now = new DateTime();
        $future = new DateTime("+1 day");
        $payload = [
            'exp' => $future->getTimestamp(),
            'iat' => $now->getTimestamp(),
            'sub' => $user->id,
        ];
        $secret = $_ENV['JWT_SECRET'];
        return JWT::encode($payload, $secret, 'HS256');
    }
}