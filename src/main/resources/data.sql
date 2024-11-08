INSERT INTO usuario (correo, password, username)
VALUES ('juan.perez@example.com', 'juan123', 'juanperez'),
       ('maria.gomez@example.com', 'maria456', 'mariagomez'),
       ('pedro.lopez@example.com', 'pedro789', 'pedrolopez'),
       ('luisa.martinez@example.com', 'luisa101', 'luisa101'),
       ('carlos.sanchez@example.com', 'carlos234', 'carlossanchez'),
       ('ana.torres@example.com', 'ana567', 'anatorres'),
       ('jorge.ramirez@example.com', 'jorge890', 'jorgeramirez'),
       ('patricia.diaz@example.com', 'patricia123', 'patriciadiaz'),
       ('luis.garcia@example.com', 'luis345', 'luisgarcia'),
       ('sofia.morales@example.com', 'sofia678', 'sofia.morales') ON CONFLICT (username) DO NOTHING;