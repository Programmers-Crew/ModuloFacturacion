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

DELIMITER $$
	create procedure SpBuscarClientesNIt(nit varchar(9))
		BEGIN
			select clienteId, clienteNit, clienteNombre
				from Clientes
					where clienteNit = nit;
		END $$
DELIMITER ;


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
	create procedure SpAgregarProveedores(proveedores int(100),nombre varchar(50), telefono varchar(8))
		BEGIN
			insert into Proveedores(proveedorId,proveedorNombre, proveedorTelefono)
				values (proveedores , nombre , telefono);
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpActualizarProveedor(idBuscado int(100), nuevoCodigo int(100),nuevoNombre varchar(50), nuevoTelefono varchar(8))
		BEGIN
			update Proveedores
				set proveedorId = nuevoCodigo ,proveedorNombre = nuevoNombre, proveedorTelefono = nuevoTelefono
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



#-------------------------- Entidad de productos
DELIMITER $$
	create procedure SpListarProductos()
		BEGIN
			select pr.productoId, pr.productoDesc, p.proveedorNombre, cp.categoriaNombre , pr.productoPrecio
				from Productos as pr
					inner join Proveedores as p
						on pr.proveedorId = p.proveedorId
							inner join CategoriaProductos as cp
								on pr.categoriaId = cp.categoriaId order by pr.productoId ASC;
        END $$
DELIMITER ;



DELIMITER $$
	create procedure SpAgregarProductos(id int(100), descr varchar(50), provId int(100), catId int(100), precio decimal(10,2))
		BEGIN
			insert into Productos(productoId, productoDesc, proveedorId, categoriaId, productoPrecio)
				values(id, descr, provId, catId, precio);
        END $$
DELIMITER ;



DELIMITER $$
	create procedure SpActualizarProductos(idBuscado int(100),id int(100), descr varchar(50), precio decimal(10,2))
		BEGIN
			update Productos
				set productoId = id, productoDesc = descr, productoPrecio = precio
					where productoId = idBuscado;
		END $$
DELIMITER ;



DELIMITER $$
	create procedure SpBuscarProductos(idBuscado int(100))
		BEGIN
			select pr.productoId, pr.productoDesc, p.proveedorNombre, cp.categoriaNombre , pr.productoPrecio
				from Productos as pr
					inner join Proveedores as p
						on pr.proveedorId = p.proveedorId
							inner join CategoriaProductos as cp
								on pr.categoriaId = cp.categoriaId
									where pr.productoId = idBuscado;
        END $$
DELIMITER ;

DELIMITER $$
create procedure SpBuscarProductosNombre(nombreProductos varchar(50))
		BEGIN
			select pr.productoId, pr.productoDesc, p.proveedorNombre, cp.categoriaNombre , pr.productoPrecio
				from Productos as pr
					inner join Proveedores as p
						on pr.proveedorId = p.proveedorId
							inner join CategoriaProductos as cp
								on pr.categoriaId = cp.categoriaId
									where pr.productoDesc = nombreProductos;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpEliminarProductos(idBuscado int(100))
		BEGIN
			delete from Productos 
				where productoId = idBuscado;
		END $$
DELIMITER ;

DELIMITER $$
	CREATE PROCEDURE spVerificarCategoria(categoria varchar(50))
		BEGIN
			select * from categoriaProductos as cp where cp.categoriaNombre = categoria;
        END $$
        
DELIMITER ;
DELIMITER $$
	CREATE PROCEDURE spVerificarProveedores(proveedor varchar(50))
		BEGIN
			select * from proveedores as p where p.proveedorNombre = proveedor;
        END $$
        
DELIMITER ;


#----------------- Entidad Inventario de productos
DELIMITER $$
	create procedure SpListarInventarioProductos()
		BEGIN
			select p.productoId, ip.inventarioProductoCant, pr.proveedorNombre , p.productoDesc, ep.estadoProductoDesc
				from InventarioProductos as ip
					inner join Productos as p
						on ip.productoId = p.productoId
							inner join EstadoProductos as ep
								on ip.estadoProductoId = ep.estadoProductoId
									inner join Proveedores as pr
										on p.proveedorId = pr.proveedorId;
        END $$
DELIMITER ;


DELIMITER $$	
	create procedure SpAgregarInventarioProductos(cant int(100), producto int(100), estado tinyint(1))
		BEGIN
			insert into InventarioProductos(inventarioProductoCant, productoId, estadoProductoId)
				values(cant, producto, estado);
        END $$
DELIMITER ;



DELIMITER $$
	create procedure SpActualizarInventarioProductos(idBuscado int(100), cant int(100),estado tinyint(1))
		BEGIN
			update InventarioProductos
				set inventarioProductoCant = cant, estadoProductoId = estado
					where productoId = idBuscado;
        END $$
DELIMITER ;



DELIMITER $$
	create procedure SpBuscarInventarioProductos(idBuscado int(100))
		BEGIN
			select p.productoId,ip.inventarioProductoCant, pr.proveedorNombre , p.productoDesc, ep.estadoProductoDesc
				from InventarioProductos as ip
					inner join Productos as p
						on ip.productoId = p.productoId
							inner join EstadoProductos as ep
								on ip.estadoProductoId = ep.estadoProductoId
									inner join Proveedores as pr
										on p.proveedorId = pr.proveedorId
											where p.productoId = idBuscado;
        END $$
DELIMITER ;



DELIMITER $$
	create procedure SpEliminarInventarioProductos(idBuscado int(100))
		BEGIN
			delete from InventarioProductos
				where productoId = idBuscado;
        END $$
DELIMITER ;



#-------------- Entidad Usuario
DELIMITER $$
	create procedure SpListarUsuario()
		BEGIN 
			select usuarioId, usuarioNombre, usuarioPassword, tipoUsuario
				from Usuarios, tipousuario 
					where Usuarios.tipoUsuarioId = tipousuario.tipoUsuarioId order by usuarioId ASC;
        END $$
DELIMITER ;



DELIMITER $$
	create procedure SpAgregarUsuario(nombre varchar(30), pass varchar(40), tipoUsuario tinyint(1))
		BEGIN
			insert into Usuarios(usuarioNombre, usuarioPassword,tipoUsuarioId)
				values(nombre, pass,tipousuario);
        END $$
DELIMITER ;



DELIMITER $$
	create procedure SpActualizarUsuario(idBuscado int(100), nombre varchar(30), pass varchar(30),tipoUsuario tinyint(1))
		BEGIN
			update Usuarios
				set usuarioNombre = nombre, usuarioPassword = pass, tipoUsuarioId = tipoUsuario
					where usuarioId = idBuscado;
        END $$
DELIMITER ;



DELIMITER $$
	create procedure SpBuscarUsuario(idBuscado int(100))
		BEGIN
			select usuarioId, usuarioNombre, usuarioPassword, tipoUsuario
				from Usuarios,tipousuario
					where usuarioId = idBuscado and tipousuario.tipoUsuarioId = usuarios.tipoUsuarioId;
        END $$
DELIMITER ;



DELIMITER $$
	create procedure SpEliminarUsuarios(idBuscado int(100))
		BEGIN
			delete from Usuarios
				where usuarioId = idBuscado;
        END $$
DELIMITER ;

#------------------ ENTIDAD TIPO USUARIO
DELIMITER $$
	CREATE PROCEDURE spListarTipoUsuario()
		BEGIN
			SELECT * FROM tipoUsuario;
        END $$
DELIMITER ;

#-------------------- Entidad Facturas
DELIMITER $$
	create procedure SpListarFacturas()
		BEGIN
			select f.facturaId, c.clienteNombre, f.facturaFecha, u.usuarioNombre, f.facturaTotalNeto, f.facturaTotalIva, f.facturaTotal
				from Facturas as f
					inner join Clientes as c
						on  f.clienteId = c.clienteId
							inner join Usuarios as u 
								on f.usuarioId = u.usuarioId;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpAgregarFacturas(id int(100), cliente int(100), fecha date, usuario int(100), neto decimal(10,2), iva decimal(10,2), total decimal(10,2))
		BEGIN
			insert into Facturas(facturaId, clienteId, facturaFecha, usuarioId, facturaTotalNeto, facturaTotalIva, facturaTotal)
				values(id, cliente, fecha, usuario, neto, iva, total);
        END $$
DELIMITER ;



DELIMITER $$
	create procedure SpBuscarFacturas(idBuscado int(100))
		BEGIN
			select f.facturaId, c.clienteNombre, f.facturaFecha, u.usuarioNombre, f.facturaTotalNeto, f.facturaTotalIva, f.facturaTotal
				from Facturas as f
					inner join Clientes as c
						on  f.clienteId = c.clienteId
							inner join Usuarios as u 
								on f.usuarioId = u.usuarioId
									where f.facturaId = idBuscado;
		END $$
DELIMITER ;



DELIMITER $$
	create procedure SpListarFacturaDetalle()
		BEGIN
			select fd.facturaDetalleId, f.facturaId, p.productoId, fd.cantidad, fd.precios
				from FacturaDetalle as fd
					inner join Facturas as f
						on fd.facturaId = f.facturaId
							inner join Productos as p
								on fd.productoId = p.productoId;
        END $$
DELIMITER ;



DELIMITER $$
	create procedure SpAgregarDetalleFacturas(id int(100), factura int(100), prodcutos int(100), cantidad int(100), precios decimal(10,2))
		BEGIN
			insert into FacturaDetalle(facturaDetalleId, facturaId, productoId, cantidad, precios)
				values(id, factura, productos, cantidad, precios);
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpBuscarDetalleFacturas(idBuscado int(100))
		BEGIN
			select fd.facturaDetalleId, f.facturaId, p.productoId, fd.cantidad, fd.precios
				from FacturaDetalle as fd
					inner join Facturas as f
						on fd.facturaId = f.facturaId
							inner join Productos as p
								on fd.productoId = p.productoId
									where facturaDetalleId = idBuscado;
        END $$
DELIMITER ;


##--------------------- FILTROS -------------------------------

#=== Inventario =====

#==================== Buscar en inventario por el codigo de producto
DELIMITER $$
	create procedure SpBuscarPorCodigoInventario(idBuscado int(100))
		BEGIN
			select ip.inventarioProductoId, ip.inventarioProductoCant, pr.proveedorNombre , p.productoDesc , ep.estadoProductoDesc
				from InventarioProductos as ip
					inner join Proveedores as pr
						on ip.proveedorId = pr.proveedorId
							inner join Productos as p
								on ip.productoId = p.productoId
									inner join EstadoProductos as ep
										on ip.estadoProductoId = ep.estadoProductoId
											where ip.productoId = p.productoId and ip.productoId = idBuscado;
        END $$
DELIMITER ;



#==================== Buscar en inventario por el nombre de producto
DELIMITER $$
	create procedure SpBuscarPorNombreInventario(nombre varchar(50))
		BEGIN
			select ip.inventarioProductoId, ip.inventarioProductoCant, pr.proveedorNombre , p.productoDesc , ep.estadoProductoDesc
				from InventarioProductos as ip
					inner join Proveedores as pr
						on ip.proveedorId = pr.proveedorId
							inner join Productos as p
								on ip.productoId = p.productoId
									inner join EstadoProductos as ep
										on ip.estadoProductoId = ep.estadoProductoId
											where p.productoDesc = nombre and ip.productoId = p.productoId;
        END $$
DELIMITER ;



#==================== Buscar en inventario por el nombre de proveedor
DELIMITER $$
	create procedure SpBuscarPorProveedorInventario(nombre varchar(50))
		BEGIN
			select ip.inventarioProductoId, ip.inventarioProductoCant, pr.proveedorNombre , p.productoDesc , ep.estadoProductoDesc
				from InventarioProductos as ip
					inner join Proveedores as pr
						on ip.proveedorId = pr.proveedorId
							inner join Productos as p
								on ip.productoId = p.productoId
									inner join EstadoProductos as ep
										on ip.estadoProductoId = ep.estadoProductoId
											where pr.proveedorNombre = nombre and ip.proveedorId = pr.proveedorId;
        END $$
DELIMITER ;



#==================== Buscar en inventario por el codigo de proveedor
DELIMITER $$
	create procedure SpBuscarPorProveedorCodigoInventario(idBuscado int(100))
		BEGIN
			select ip.inventarioProductoId, ip.inventarioProductoCant, pr.proveedorNombre , p.productoDesc , ep.estadoProductoDesc
				from InventarioProductos as ip
					inner join Proveedores as pr
						on ip.proveedorId = pr.proveedorId
							inner join Productos as p
								on ip.productoId = p.productoId
									inner join EstadoProductos as ep
										on ip.estadoProductoId = ep.estadoProductoId
											where pr.proveedorId = idBuscado and ip.proveedorId = pr.proveedorId;
        END $$
DELIMITER ; 



#==================== Buscar en inventario por el codigo de estado 
DELIMITER $$
	create procedure SpBuscarPorEstadoCodigoInventario(idBuscado int(100))
		BEGIN
			select ip.inventarioProductoId, ip.inventarioProductoCant, pr.proveedorNombre , p.productoDesc , ep.estadoProductoDesc
				from InventarioProductos as ip
					inner join Proveedores as pr
						on ip.proveedorId = pr.proveedorId
							inner join Productos as p
								on ip.productoId = p.productoId
									inner join EstadoProductos as ep
										on ip.estadoProductoId = ep.estadoProductoId
											where ep.estadoProductoId = idBuscado and ip.estadoProductoId = ep.estadoProductoId;
        END $$
DELIMITER ; 



#==================== Buscar en inventario por el nombre de estado 
DELIMITER $$
	create procedure SpBuscarPorEstadoNombreInventario(nombre varchar(100))
		BEGIN
			select ip.inventarioProductoId, ip.inventarioProductoCant, pr.proveedorNombre , p.productoDesc , ep.estadoProductoDesc
				from InventarioProductos as ip
					inner join Proveedores as pr
						on ip.proveedorId = pr.proveedorId
							inner join Productos as p
								on ip.productoId = p.productoId
									inner join EstadoProductos as ep
										on ip.estadoProductoId = ep.estadoProductoId
											where ep.estadoProductoDesc = nombre and ip.estadoProductoId = ep.estadoProductoId;
        END $$
DELIMITER ;


#========== Facturas

DELIMITER $$
	create procedure SpBuscarDetalleFacturasFecha(fechaInicio date, FechaFinal date)
		BEGIN
			select f.facturaId, f.facturaFecha, f.facturaTotalNeto, f.facturaTotalIva, f.facturaTotal, c.clienteNit, c.clienteNombre, p.productoDesc, fd.cantidad, fd.precios
				from facturas as f
					inner join clientes as c
						on f.clienteId = c.clienteId
							inner join facturadetalle as fd
								on  f.facturaId = fd.facturaDetalleId
									inner join productos as p
										on fd.productoId = p.productoId
											where f.facturaFecha between fechaInicio and FechaFinal;
        END $$
DELIMITER ;

	DELIMITER $$

	CREATE PROCEDURE  SpLogin(usuarioNom varchar(30), pass varchar(40))
		BEGIN
			select u.usuarioNombre, u.usuarioPassword
				from usuarios as u
					where u.usuarioNombre = usuarioNom and u.usuarioPassword = pass;
		END $$
	DELIMITER ;

	DELIMITER $$

	CREATE PROCEDURE  SpLoginValidar(usuarioNom varchar(30))
		BEGIN
			select u.tipoUsuarioId
				from usuarios as u
					where u.usuarioNombre = usuarioNom;
		END $$
	DELIMITER ;