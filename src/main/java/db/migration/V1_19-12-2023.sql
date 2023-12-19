select constraint_name
from information_schema.constraint_column_usage
where table_name = 'usuarios_acessos' and column_name = 'acesso_id'
  and constraint_name <> 'unique_acesso_user';


alter table usuarios_acessos  drop CONSTRAINT "uk_qs91qokws6i46m1vnsoakivh1";

-- apagar constraint errada que é criada automaticamente com o JPA.