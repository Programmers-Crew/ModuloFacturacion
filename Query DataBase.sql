create database ProgrammersBilling;

create table Clientes(
	clienteId	int(100) primary key auto_increment,
	clienteNit	varchar(9) unique not null,
	clienteNombre varchar(25) not null

);


create table EstadoProductos(
	estadoProductoId tinyint(1) primary key auto_increment,
    estadoProductoDesc varchar(100) unique not null
);


create table Proveedores(
	proveedorId int(100) primary key,
    proveedorNombre varchar(50) unique not null,
	proveedorTelefono varchar(8) unique not null
);

create table CategoriaProductos(
	categoriaId int(100) primary key,
    categoriaNombre varchar(50) unique not null
);


create table Productos(

	productoId	int(100) primary key,
    productoDesc varchar(50) not null,
	proveedorId int(100) not null,
    categoriaId int(100) not null,
    productoPrecio decimal(10,2) not null,

    CONSTRAINT FK_ProveedorProductos FOREIGN KEY (proveedorId) REFERENCES Proveedores(proveedorId),
    CONSTRAINT FK_CategoriaProductos FOREIGN KEY (categoriaId) REFERENCES CategoriaProductos(categoriaId)
);


create table InventarioProductos(
	inventarioProductoId int(100) primary key auto_increment,
    inventarioProductoCant int(100) not null,
    productoId int(100) not null,
    estadoProductoId tinyint(1) not null,
    
	CONSTRAINT FK_ProductoInventario FOREIGN KEY (productoId) REFERENCES Productos(productoId),
	CONSTRAINT FK_EstadoProductoInventario FOREIGN KEY (estadoProductoId) REFERENCES EstadoProductos(estadoProductoId)
);

create table TipoUsuario(
	tipoUsuarioId tinyint(1) primary key auto_increment,
    tipoUsuario varchar(20) not null unique
);

create table Usuarios(
	usuarioId int(10) primary key not null auto_increment,
    usuarioNombre varchar(30) not null unique,
    usuarioPassword varchar(40)  not null,
    tipoUsuarioId tinyint(1) not null,
    CONSTRAINT FK_UsuariosTipoUsuario FOREIGN KEY (tipoUsuarioId) REFERENCES TipoUsuario(tipoUsuarioId)
);


create table Facturas(
	facturaId int(100) primary key,
	facturaDetalleId int(100) not null, 
    clienteId int(100) not null,
    facturaFecha date not null,
    usuarioId int(100) not null,
    facturaTotalNeto decimal(10,2) not null,
    facturaTotalIva decimal(10,2) not null,
    facturaTotal decimal(10,2) not null,
    
	CONSTRAINT FK_facturaDetalle FOREIGN KEY (facturaDetalleId) REFERENCES Facturas(facturaId),
	CONSTRAINT FK_clienteFactura FOREIGN KEY (clienteId) REFERENCES Clientes(clienteId),
	CONSTRAINT FK_usuarioFactura FOREIGN KEY (usuarioId) REFERENCES Usuarios(usuarioId)
);


create table FacturaDetalle(
	facturaDetalleId int(100) primary key,
    productoId int(100) not null,
    cantidad int(100) not null, 
    totalParcial decimal(10,2),
    
	CONSTRAINT FK_productoFacDetalle FOREIGN KEY (productoId) REFERENCES Productos(productoId)

);

create table FacturaDetalleBackUp(
	facturaDetalleIdBackup int primary key auto_increment,
    productoIdBackup int(100) not null,
    cantidadBackup int(100) not null, 
    totalParcialBackup decimal(10,2) not null,
    
	CONSTRAINT FK_productoFacDetalleBackup FOREIGN KEY (productoIdBackup) REFERENCES Productos(productoId)
);
