#============================================ STORED PROCEDURE ===============================

#--- Entidad Clientes

DELIMITER $$
create procedure SpListarClientes()
	BEGIN
		select clienteId, clienteNit, clienteNombre, clienteDireccion
			from clientes
				order by clienteId asc;
    END $$
DELIMITER ;


DELIMITER $$
	create procedure SpAgregarClientes(nit varchar(9), nombre varchar(25), direccion varchar(100))
		BEGIN
			insert into Clientes(clienteNit, clienteNombre, clienteDireccion)
				value(nit, nombre, direccion);
        END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE SpAgregarClientesSinDireccion(nit varchar(9), nombre varchar(25))
		BEGIN
			insert into Clientes(clienteNit, clienteNombre)
				value(nit, nombre);
        END $$
DELIMITER ;

DELIMITER $$
	create procedure SpActualizarClientes(idBuscado int(100), nuevoNit varchar(9), nombre varchar(25), direccion varchar(100))
		BEGIN
			update Clientes
				set clienteNit = nuevoNit, clienteNombre = nombre, clienteDireccion = direccion
					where clienteId = idBuscado;
        END $$
DELIMITER ;



DELIMITER $$
	create procedure SpEliminarClientes(idBuscado int(100))
		BEGIN
			delete from Clientes
				where clienteId = idBuscado;
        END $$
DELIMITER ;



DELIMITER $$
	create procedure SpBuscarClientes(idBuscado int(100))
		BEGIN
			select clienteId, clienteNit, clienteNombre, clienteDireccion
				from Clientes
					where clienteId = idBuscado
						order by clienteId asc;
		END $$
DELIMITER ;

DELIMITER $$
	create procedure SpBuscarClientesNIt(nit varchar(9))
		BEGIN
			select clienteId, clienteNit, clienteNombre, clienteDireccion
				from Clientes
					where clienteNit = nit
						order by clienteId asc;
		END $$
DELIMITER ;


#------------- Entidad Estado Producto

DELIMITER $$
	create procedure SpListarEstadoProductos()
		BEGIN
			select estadoProductoId, estadoProductoDesc
				from EstadoProductos
					order by estadoProductoId asc;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpAgregarEstadoProducto(descripcion varchar(1000))
		BEGIN
			insert into EstadoProductos(estadoProductoDesc)
				values(descripcion);
		END $$
DELIMITER ;

call spAgregarEstadoProducto("EN EXISTENCIA");
call spAgregarEstadoProducto("AGOTADO");

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
					where estadoProductoId = idBuscado
						order by estadoProductoId asc;
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
				from Proveedores
					order by proveedorId asc;
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
					where proveedorId = idBuscado
						order by proveedorId asc;
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
				from CategoriaProductos
					order by categoriaId asc;
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
            from categoriaproductos
				where categoriaId = idBuscado
					order by categoriaId asc;
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
								on pr.categoriaId = cp.categoriaId 
									order by pr.productoId ASC;
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
									where pr.productoId = idBuscado
										order by pr.productoId ASC;
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
									where pr.productoDesc = nombreProductos
										order by pr.productoId ASC;
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
			select * from categoriaProductos as cp
				where cp.categoriaNombre = categoria;
        END $$
        
DELIMITER ;
DELIMITER $$
	CREATE PROCEDURE spVerificarProveedores(proveedor varchar(50))
		BEGIN
			select * from proveedores as p 
				where p.proveedorNombre = proveedor;
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
										on p.proveedorId = pr.proveedorId
											order by p.productoId ASC;
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
											where p.productoId = idBuscado
												order by p.productoId ASC;
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
					where Usuarios.tipoUsuarioId = tipousuario.tipoUsuarioId 
						order by usuarioId ASC;
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
					where usuarioId = idBuscado
						and tipousuario.tipoUsuarioId = usuarios.tipoUsuarioId
							order by usuarioId ASC;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpBuscarUsuarioId(usuarioNombre varchar(30))
		BEGIN
			select usuarioId, usuarioNombre, usuarioPassword, tipoUsuario
				from Usuarios,tipousuario
					where usuarioNombre= Usuarios.usuarioNombre 
						and tipousuario.tipoUsuarioId = usuarios.tipoUsuarioId
							order by usuarioId ASC;
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
			SELECT * FROM tipoUsuario
				order by tipoUsuarioId ASC;
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
								on f.usuarioId = u.usuarioId
									order by f.facturaId asc;
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
	create procedure SpAgregarFacturasPrueba(id int(100), cliente int(100),usuario int(100), neto decimal(10,2), iva decimal(10,2), total decimal(10,2))
		BEGIN
			insert into Facturas(facturaId, clienteId,usuarioId, facturaTotalNeto, facturaTotalIva, facturaTotal)
				values(id, cliente,usuario, neto, iva, total);
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpBuscarcodigoProducto(nombre varchar(50))
			BEGIN
				select productoId
					from productos
						where productoDesc = nombre
							order by productoId asc;
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
									where f.facturaId = idBuscado
										order by f.facturaId asc;
		END $$
DELIMITER ;



DELIMITER $$
	create procedure SpListarFacturaDetalle()
		BEGIN
			select fd.facturaDetalleId, p.productoId, fd.cantidad, fd.totalParcial
				from FacturaDetalle as fd
							inner join Productos as p
								on fd.productoId = p.productoId
									order by fd.facturaDetalleId asc;
        END $$
DELIMITER ;



DELIMITER $$
	create procedure SpAgregarDetalleFacturas(id int(100), prodcutoId int(100), cantidad int(100), totalParcial decimal(10,2))
		BEGIN
			insert into FacturaDetalle(facturaDetalleId,productoId,cantidad,totalParcial)
				values(id,prodcutoId, cantidad, totalParcial);
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpBuscarDetalleFacturas(idBuscado int(100))
		BEGIN
			select fd.facturaDetalleId, f.facturaId, p.productoId, fd.cantidad, fd.totalParcial
				from FacturaDetalle as fd
					inner join Facturas as f
						on fd.facturaDetalleId = f.facturaDetalleId
							inner join Productos as p
								on fd.productoId = p.productoId
									where fd.facturaDetalleId = idBuscado
										order by fd.facturaDetalleId asc;
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
											where ip.productoId = p.productoId 
												and ip.productoId = idBuscado
													order by ip.inventarioProductoId;
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
											where p.productoDesc = nombre and ip.productoId = p.productoId
												order by ip.inventarioProductoId asc;
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
											where pr.proveedorNombre = nombre and ip.proveedorId = pr.proveedorId
												order by ip.inventarioProductoId;
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
											where pr.proveedorId = idBuscado 
												and ip.proveedorId = pr.proveedorId
													order by ip.inventarioProductoId asc;
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
											where ep.estadoProductoId = idBuscado 
												and ip.estadoProductoId = ep.estadoProductoId
													order by ip.inventarioProductoId asc ;
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
											where ep.estadoProductoDesc = nombre 
												and ip.estadoProductoId = ep.estadoProductoId
													order by ip.inventarioProductoId asc;
        END $$
DELIMITER ;


#========== Facturas

DELIMITER $$
	create procedure SpBuscarDetalleFacturasFecha(fechaInicio date, FechaFinal date)
		BEGIN
			select distinct f.facturaId, f.facturaFecha, f.facturaTotalNeto, f.facturaTotalIva, f.facturaTotal
				from facturas as f
					where f.facturaFecha between fechaInicio and FechaFinal
						order by f.facturaId asc;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpBuscarClienteFacturaFecha(idBuscado int(5))
		BEGIN	
			select f.facturaId, c.clienteNit, c.clienteNombre, pr.productoDesc, fd.cantidad, pr.productoPrecio
				from facturas as f
					inner join clientes as c
						on c.clienteId = f.clienteId
							inner join facturadetalle as fd
								on f.facturaDetalleId = fd.facturaDetalleId
									inner join productos as pr
										on pr.productoId = fd.productoId
											where f.facturaId = idBuscado
												order by f.facturaId asc;
        END $$
DELMITER ;
call SpBuscarClienteFacturaFecha(00001);

DELIMITER $$
	create procedure SpListarBusquedasFacturasPorId(idBuscado int(5))
		BEGIN
			select distinct f.facturaId, f.facturaTotalNeto, f.facturaTotalIva, f.facturaTotal, f.facturaFecha
				from facturas as f
					where f.facturaId = idBuscado
                     order by f.facturaId asc;
        END $$
DELIMITER ;



DELIMITER $$
	create procedure SpListarBusquedasFacturas()
		BEGIN
			select distinct f.facturaId, f.facturaTotalNeto, f.facturaTotalIva, f.facturaTotal, f.facturaFecha
				from facturas as f
					order by f.facturaId asc;
		END $$
DELIMITER ;


# ============ LOGIN
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

#====================================== Entidad de Backup

DELIMITER $$
	create procedure SpListarBackup()
		BEGIN
			select fdb.facturaDetalleIdBackup, p.productoDesc, fdb.cantidadBackup , p.productoPrecio ,fdb.totalParcialBackup
				from facturadetallebackup as fdb
							inner join Productos as p
								on fdb.productoIdBackup = p.productoId;
        END $$
DELIMITER ;
DELIMITER $$
	create procedure spEditarBackup(idBuscado INT(5),prodId INT(5), cantidad INT(5), totalParcial decimal(10,2) )
		BEGIN
			update facturadetallebackup
				set productoIdBackup = prodId, productoIdBackup = prodId, cantidadBackup = cantidad, totalParcialBackup = totalParcial
					where facturaDetalleIdBackup = idBuscado;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpAgregarBackup(productoId int(11), cantidad int(11), totalParcial decimal(10,2))
		BEGIN				
                insert into facturadetallebackup(productoIdBackup,cantidadBackup,totalParcialBackup)
				values(productoId, cantidad, totalParcial);
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpTransferirBackup()
		BEGIN 
			insert into facturadetalle(facturaDetalleId,productoId,cantidad,totalParcial)
				select facturaDetalleIdBackup,productoIdBackup,cantidadBackup,totalParcialBackup 
					from facturadetallebackup;
		END $$
DELIMITER ;

DELIMITER $$
	create procedure SpEliminarBackup()
		BEGIN 
			delete from facturadetallebackup where facturaDetalleIdBackup>0;
		END $$
DELIMITER ;


DELIMITER $$
	create procedure SpAgregarFactura(codigoFactura int(5),clienteId int(5), facturaFecha date, usuarioId int(5), facturaTotalNeto decimal(10,2), facturaTotalIva decimal(10,2), facturaTotal decimal(10,2))
		BEGIN 
			insert into facturas (facturaId, facturaDetalleId, clienteId, facturaFecha, usuarioId,facturaTotalNeto,facturaTotalIva,facturaTotal)
				select codigoFactura, fb.facturaDetalleIdBackup, clienteId, facturaFecha, usuarioId,facturaTotalNeto,facturaTotalIva,facturaTotal
					from facturadetallebackup as fb;
		END $$
DELIMITER ;


DELIMITER $$
	CREATE PROCEDURE SpSumarBackup()
		BEGIN
			select sum(totalParcialBackup) from facturadetallebackup;
        END $$
DELIMITER ;

insert into tipousuario values(0,"Administrador"),(0,"Empleado");

insert into usuarios values(0,"admin", "admin", 1);
INSERT INTO clientes(clienteNit,clienteNombre) values("C/F","C/F");
DELIMITER $$
	create procedure SpBuscarCodigoEstado(nombre varchar(100))
		BEGIN
			select estadoProductoId
				from estadoproductos
					where estadoProductoDesc = nombre;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpCorteDeCaja(fechaCorte date)
		BEGIN
			select distinct f.facturaId, c.clienteNombre, c.clienteNit,  f.facturaFecha, u.usuarioNombre, f.facturaTotalNeto, f.facturaTotalIva, f.facturaTotal
				from facturas as f
					inner join clientes as c
						on f.clienteId = c.clienteId
							inner join usuarios as u
								on f.usuarioId = u.usuarioId
									where facturaFecha = fechaCorte
										order by f.facturaId asc;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpTotalVendio(fechaCorte date)
		BEGIN
			select facturaFecha, sum(facturaTotalNeto) as 'Total Neto Vendido', sum(facturaTotalIva) as 'Total Iva Vendido' , sum(facturaTotal) as 'Total Vendido', count(f.facturaId), sum(fd.cantidad)
				from facturas as f
					inner join facturadetalle as fd
						on f.facturaDetalleId = fd.facturaDetalleId
							where facturaFecha = fechaCorte;
		END $$
DELIMITER ;

DELIMITER $$
	create procedure SpCorteDeCajaDetalle(facturaId int)
		BEGIN
			select productoDesc, productoPrecio ,cantidad
				from facturadetalle as fd
					inner join productos as p
						on fd.productoId = p.productoId
							inner join facturas as f
								on f.facturaDetalleId = fd.facturaDetalleId
									where f.facturaId = facturaId;
        END $$ 
DELIMITER ;

call SpCorteDeCajaDetalle(29);


DELIMITER $$
	create procedure SpInventarioConteo()
		BEGIN 
			update inventarioproductos as ip
				inner join facturadetallebackup as fd
					set ip.inventarioProductoCant = ip.inventarioProductoCant - fd.cantidadBackup
						where ip.productoId = fd.productoIdBackup;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpSumaProductos(idBuscado int, cantidad int)
		BEGIN 
			update inventarioproductos as ip
					set ip.inventarioProductoCant = ip.inventarioProductoCant + cantidad
						where ip.productoId = idBuscado ;
        END $$
DELIMITER ;

 
DELIMITER $$
	create procedure SpDatoReporteVentas(fechaCorte date)
		BEGIN 
			select facturaFecha ,count(distinct f.facturaId), sum(fd.cantidad)
				from facturas as f
					inner join facturadetalle as fd
						on f.facturaDetalleId = fd.facturaDetalleId
							where f.facturaFecha = fechaCorte;
        END $$
DELIMITER ;


