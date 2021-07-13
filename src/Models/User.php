<?php

use Illuminate\Database\Eloquent\Model;

class User extends Model {
    protected $table = 'users';

    public $timestamps = true;

    protected $fillable = [
        'first_name',
        'last_name',
        'email'
    ];

    protected $guarded = [
        'password'
    ];

    public function role()
    {
        return $this->hasOne(Role::class);
    }
}