create database ProgrammersBilling;

create table Clientes(
	clienteId	int(5)  UNSIGNED ZEROFILL primary key auto_increment,
	clienteNit	varchar(9) unique not null,
	clienteNombre varchar(25) not null,
	clienteDireccion varchar(100) not null DEFAULT 'Ciudad de Guatemala'
);


create table EstadoProductos(
	estadoProductoId tinyint(1) primary key auto_increment,
    estadoProductoDesc varchar(100) unique not null
);


create table Proveedores(
	proveedorId int(5) UNSIGNED ZEROFILL primary key,
    proveedorNombre varchar(50) unique not null,
	proveedorTelefono varchar(8) unique not null
);

create table CategoriaProductos(
	categoriaId int(5) UNSIGNED ZEROFILL primary key,
    categoriaNombre varchar(50) unique not null
);


create table Productos(

	productoId	int(5) UNSIGNED ZEROFILL primary key,
    productoDesc varchar(50) not null,
	proveedorId int(5) UNSIGNED ZEROFILL not null,
    categoriaId int(5) UNSIGNED ZEROFILL not null,
    productoPrecio decimal(10,2) not null,

    CONSTRAINT FK_ProveedorProductos FOREIGN KEY (proveedorId) REFERENCES Proveedores(proveedorId),
    CONSTRAINT FK_CategoriaProductos FOREIGN KEY (categoriaId) REFERENCES CategoriaProductos(categoriaId)
);


create table InventarioProductos(
	inventarioProductoId int(5) UNSIGNED ZEROFILL primary key auto_increment,
    inventarioProductoCant int(5)  not null,
    productoId int(5) UNSIGNED ZEROFILL not null unique,
    estadoProductoId tinyint(1) not null,
    
	CONSTRAINT FK_ProductoInventario FOREIGN KEY (productoId) REFERENCES Productos(productoId),
	CONSTRAINT FK_EstadoProductoInventario FOREIGN KEY (estadoProductoId) REFERENCES EstadoProductos(estadoProductoId)
);

create table TipoUsuario(
	tipoUsuarioId tinyint(1) primary key auto_increment,
    tipoUsuario varchar(20) not null unique
);

create table Usuarios(
	usuarioId int(5) UNSIGNED ZEROFILL primary key not null auto_increment,
    usuarioNombre varchar(30) not null unique,
    usuarioPassword varchar(40)  not null,
    tipoUsuarioId tinyint(1) not null,
    CONSTRAINT FK_UsuariosTipoUsuario FOREIGN KEY (tipoUsuarioId) REFERENCES TipoUsuario(tipoUsuarioId)
);

create table FacturaDetalle(
	facturaDetalleId int(5) UNSIGNED ZEROFILL primary key,
    productoId int(5) UNSIGNED ZEROFILL not null,
    cantidad int(5)  not null, 
    totalParcial decimal(10,2),
    
	CONSTRAINT FK_productoFacDetalle FOREIGN KEY (productoId) REFERENCES Productos(productoId)

);


create table FacturaDetalleBackUp(
	facturaDetalleIdBackup int(5)UNSIGNED ZEROFILL primary key auto_increment ,
    productoIdBackup int(5) UNSIGNED ZEROFILL not null,
    cantidadBackup int(5)  not null, 
    totalParcialBackup decimal(10,2) not null,
    
	CONSTRAINT FK_productoFacDetalleBackup FOREIGN KEY (productoIdBackup) REFERENCES Productos(productoId)
);

create table Facturas(
	codigo int(5) UNSIGNED ZEROFILL PRIMARY KEY auto_increment,
	facturaId int(5) UNSIGNED ZEROFILL,
	facturaDetalleId int(5) UNSIGNED ZEROFILL not null, 
    clienteId int(5) UNSIGNED ZEROFILL not null,
    facturaFecha date not null,
    usuarioId int(5) UNSIGNED ZEROFILL not null,
    facturaTotalNeto decimal(10,2) not null,
    facturaTotalIva decimal(10,2) not null,
    facturaTotal decimal(10,2) not null,
    
	CONSTRAINT FK_facturaDetalle FOREIGN KEY (facturaDetalleId) REFERENCES facturadetalle(facturaDetalleId),
	CONSTRAINT FK_clienteFactura FOREIGN KEY (clienteId) REFERENCES Clientes(clienteId),
	CONSTRAINT FK_usuarioFactura FOREIGN KEY (usuarioId) REFERENCES Usuarios(usuarioId)
);


