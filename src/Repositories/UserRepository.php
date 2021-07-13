<?php

namespace App\Repositories;

use User;

class UserRepository extends BaseRepository {
    
    public function getByEmailAndPassword($email, $password) {
        return User::where('email', $email)->where('password', $password)->first();
    }
}