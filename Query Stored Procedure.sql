#============================================ STORED PROCEDURE ===============================

#--- Entidad Clientes

DELIMITER $$
create procedure SpListarClientes()
	BEGIN
		select clienteId, clienteNit, clienteNombre
			from clientes;
    END $$
DELIMITER ;
call SpListarClientes();


DELIMITER $$
	create procedure SpAgregarClientes(nit varchar(9), nombre varchar(25))
		BEGIN
			insert into Clientes(clienteNit, clienteNombre)
				value(nit, nombre);
        END $$
DELIMITER ;
call SpAgregarClientes("123456789", "Juan Pedro")


DELIMITER $$
	create procedure SpActualizarClientes(idBuscado int(100), nuevoNit varchar(9), nombre varchar(25))
		BEGIN
			update Clientes
				set clienteNit = nuevoNit, clienteNombre = nombre
					where clienteId = idBuscado;
        END $$
DELIMITER ;
call SpActualizarClientes(1, "123456788", "Juan Pedro");


DELIMITER $$
	create procedure SpEliminarClientes(idBuscado int(100))
		BEGIN
			delete from Clientes
				where clienteId = idBuscado;
        END $$
DELIMITER ;
call SpEliminarClientes(1);


DELIMITER $$
	create procedure SpBuscarClientes(idBuscado int(100))
		BEGIN
			select clienteId, clienteNit, clienteNombre
				from Clientes
					where clienteId = idBuscado;
		END $$
DELIMITER ;
call SpBuscarClientes(1);


#------------- Entidad Estado Producto

DELIMITER $$
	create procedure SpListarEstadoProductos()
		BEGIN
			select estadoProductoId, estadoProductoDesc
				from EstadoProductos;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpAgregarEstadoProducto(descripcion varchar(1000))
		BEGIN
			insert into EstadoProductos(estadoProductoDesc)
				values(descripcion);
		END $$
DELIMITER ;


DELIMITER $$
	create procedure SpActualizarEstadoProducto(idBuscado tinyint(1), nuevaDesc varchar(100))
		BEGIN
			update EstadoProductos
				set estadoProductoDesc = nuevaDesc
					where estadoProductoId = idBuscado;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpBuscarEstadoProductos(idBuscado tinyint(1))
		BEGIN
			select estadoProductoId, estadoProductoDesc
				from EstadoProductos
					where estadoProductoId = idBuscado;
        END $$
DELIMITER ;



DELIMITER $$
	create procedure SpEliminarEstadoProductos(idBuscado tinyint(1))
		BEGIN
			delete from EstadoProductos
				where estadoProductoId = idBuscado;
        END $$
DELIMITER ;


#-------------- Entidad Proveedores 
DELIMITER $$
	create procedure SpListarProveedores()
		BEGIN
			select proveedorId, proveedorNombre, proveedorTelefono
				from Proveedores;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpAgregarProveedores(nombre varchar(50), telefono varchar(8))
		BEGIN
			insert into Proveedores(proveedorNombre, proveedorTelefono)
				values (nombre, telefono);
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpActualizarProveedor(idBuscado int(100), nuevoNombre varchar(50), nuevoTelefono varchar(8))
		BEGIN
			update Proveedores
				set proveedorNombre = nuevoNombre, proveedorTelefono = nuevoTelefono
					where proveedorId = idBuscado;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpBuscarProveedores(idBuscado int(100))
		BEGIN
			select proveedorId, proveedorNombre, proveedorTelefono
				from Proveedores 
					where proveedorId = idBuscado;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpEliminarProveedor(idBuscado int(100))
		BEGIN
			delete from Proveedores
				where proveedorId = idBuscado;
        END $$
DELIMITER ;


#------------------ Entidad Categoria Productos
DELIMITER $$
	create procedure SpListarCategoriaProductos()
		BEGIN
			select categoriaId, categoriaNombre
				from CategoriaProductos;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpAgregarCategoriaProductos(id int(100), nombre varchar(50))
		BEGIN
			insert into CategoriaProductos(categoriaId, categoriaNombre)
				values(id, nombre);
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpActualizarCategoriaProductos(idBuscado int(100),idNuevo int(100), nuevoNombre varchar(50))
		BEGIN
			update CategoriaProductos
				set categoriaId =idNuevo , categoriaNombre = nuevoNombre
					where categoriaId = idBuscado;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpBuscarCategoriaProductos(idBuscado int(100))
		BEGIN
			select categoriaNombre, categoriaId
				where categoriaId = idBuscado;
		END $$
DELIMITER ;



DELIMITER $$
	create procedure SpEliminarCategoriaProductos(idBuscado int(100))
		BEGIN
			delete from CategoriaProductos
				where categoriaId = idBuscado;
        END $$
DELIMITER ;
