<?php
declare(strict_types=1);

use Phinx\Migration\AbstractMigration;

final class CreateUsersTableMigration extends AbstractMigration
{
    /**
     * Change Method.
     *
     * Write your reversible migrations using this method.
     *
     * More information on writing migrations is available here:
     * https://book.cakephp.org/phinx/0/en/migrations.html#the-change-method
     *
     * Remember to call "create()" or "update()" and NOT "save()" when working
     * with the Table class.
     */
    public function change(): void
    {
        $table = $this->table('users');
        $table->addColumn('first_name', 'string', ['limit' => 255])
            ->addColumn('last_name', 'string', ['limit' => 255])
            ->addColumn('email', 'string', ['limit' => 255])
            ->addColumn('password', 'string', ['limit' => 255])
            ->addColumn('role_id', 'integer', ['null' => true])
            ->addForeignKey(['role_id'], 'roles', ['id'], ['delete' => 'SET_NULL', 'update' => 'CASCADE'])
            ->addTimestamps()
            ->create();
    }
}
