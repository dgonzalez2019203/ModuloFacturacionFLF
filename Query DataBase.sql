create database ProgrammersBilling;
use ProgrammersBilling;

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
	proveedorId varchar(7) primary key,
    proveedorNombre varchar(50) unique not null,
	proveedorTelefono varchar(8) unique not null
);

create table CategoriaProductos(
	categoriaId varchar(7) primary key,
    categoriaNombre varchar(50) unique not null
);


create table Productos(
	productoId	varchar(7) primary key,
    productoDesc varchar(50) not null,
	proveedorId varchar(7) not null,
    categoriaId varchar(7) not null,
	precioCosto decimal(10,2) not null,
    productoPrecio decimal(10,2) not null,
    CONSTRAINT FK_ProveedorProductos FOREIGN KEY (proveedorId) REFERENCES Proveedores(proveedorId),
    CONSTRAINT FK_CategoriaProductos FOREIGN KEY (categoriaId) REFERENCES CategoriaProductos(categoriaId)
);

create table InventarioProductos(
	inventarioProductoId int(10) UNSIGNED ZEROFILL primary key auto_increment,
    inventarioProductoCant int(5)  not null,
    productoId varchar(7) not null unique,
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
    productoId varchar(7) not null,
    cantidad int(5)  not null, 
    totalParcial decimal(10,2),
	CONSTRAINT FK_productoFacDetalle FOREIGN KEY (productoId) REFERENCES Productos(productoId)
);


create table FacturaDetalleBackUp(
	facturaDetalleIdBackup int(5)UNSIGNED ZEROFILL primary key auto_increment ,
    productoIdBackup varchar(7) not null,
    cantidadBackup int(5)  not null, 
    totalParcialBackup decimal(10,2) not null,
    
	CONSTRAINT FK_productoFacDetalleBackup FOREIGN KEY (productoIdBackup) REFERENCES Productos(productoId)
);


create table TipoFactua(
	tipoFactura int not null,
    tipoFacturaDesc varchar(25),
    PRIMARY KEY (tipoFactura) 
);

create table EstadoFactura(
	estadoFactura int not null,
    estadoFacturaDesc varchar(25),
    PRIMARY KEY (estadoFactura) 
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
	facturaTipo int,
    estadoFactura int default 1,
	CONSTRAINT FK_facturaDetalle FOREIGN KEY (facturaDetalleId) REFERENCES facturadetalle(facturaDetalleId),
	CONSTRAINT FK_clienteFactura FOREIGN KEY (clienteId) REFERENCES Clientes(clienteId),
	CONSTRAINT FK_usuarioFactura FOREIGN KEY (usuarioId) REFERENCES Usuarios(usuarioId),
	CONSTRAINT FK_tipoFactura FOREIGN KEY (facturaTipo) REFERENCES TipoFactua(tipoFactura),
    CONSTRAINT FK_estadoFactura FOREIGN KEY (estadoFactura) REFERENCES EstadoFactura(estadoFactura)
);

create table ChequeDetalle(
	chequeDetalleNo int(100)  auto_increment,
    chequeDetalleCuenta varchar(100) not null,
    chequeDetalleDesc varchar(200) not null, 
    chequeDetalleValor double not null,
    PRIMARY KEY (chequeDetalleNo)
);
create table ChequeDetallebackup(
	chequeDetalleNo int(100)  auto_increment,
    chequeDetalleCuenta varchar(100) not null,
    chequeDetalleDesc varchar(200) not null, 
    chequeDetalleValor double not null,
    PRIMARY KEY (chequeDetalleNo)
);

create table Cheque(
	chequeCodigo int not null auto_increment,
	chequeNo int(10)  UNSIGNED ZEROFILL,
    chequeLugar varchar(100) not null,
    chequeFecha date not null,
    chequePagoAlaOrdenDe varchar(50) not null,
    chequeMonto double not null,
	chequeDetalle int(100)  not null,
    chequeUsuario int(5) UNSIGNED ZEROFILL not null,
	PRIMARY KEY (chequeCodigo),
	CONSTRAINT FK_usuarioCheque FOREIGN KEY (chequeUsuario) REFERENCES Usuarios(usuarioId),
	CONSTRAINT FK_ChequeDetalle FOREIGN KEY (chequeDetalle) REFERENCES ChequeDetalle(chequeDetalleNo)
);

create table EstadoCredito(
	estadoCreditoId int not null,
    estadoCreditoDesc varchar(25) not null unique,
	PRIMARY KEY (estadoCreditoId)
);

create table Creditos(
	idCredito int not null auto_increment,
    creaditoFechaInicio date not null,
    creditoFechaFinal date not null,
    creditoDiasRestantes int not null,
	creditoDesc varchar(50) not null,
    creditoProveedor varchar(50) not null,
    creditoMonto double not null,
    creditoEstado int not null,
	PRIMARY KEY (idCredito),
    CONSTRAINT FK_CreditosProveedor FOREIGN KEY (creditoProveedor) REFERENCES Proveedores(proveedorId),
	CONSTRAINT FK_CreditosEstados FOREIGN KEY (creditoEstado) REFERENCES EstadoCredito(estadoCreditoId)
);
