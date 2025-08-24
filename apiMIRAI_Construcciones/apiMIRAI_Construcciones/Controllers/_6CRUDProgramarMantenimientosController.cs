using APIMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using System.Data.Entity;
using APIMIRAI_Construcciones.Models;

namespace APIMIRAI_Construcciones.Controllers
{
    [RoutePrefix("api/programaciones")]
    public class _6CRUDProgramarMantenimientosController : ApiController
    {
        private readonly PruebaAlmacenTAEPIEntities1 _db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/programaciones
        [HttpGet, Route("")]
        public IHttpActionResult GetProgramaciones(string search = null, int? tipoId = null, bool? activo = null, int page = 1, int pageSize = 50)
        {
            var q = _db.Recordatorios
                       .Include(r => r.Equipos)
                       .Include(r => r.Tareas)
                       .Include(r => r.Usuarios)
                       .Include(r => r.Prioridades)
                       .Include(r => r.TiposMantenimientos)
                       .AsQueryable();

            // Filtrar por tipo de mantenimiento si lo pasan 
            if (tipoId.HasValue) q = q.Where(p => p.idfTiposMantenimientos == tipoId.Value);

            // (activo no existe en Recordatorios en tu ejemplo; si existe descomenta/ajusta)
            // if (activo.HasValue) q = q.Where(p => p.activo == activo.Value);

            // Búsqueda libre en campos relevantes
            if (!string.IsNullOrEmpty(search))
            {
                var s = search.Trim().ToLower();
                q = q.Where(p =>
                    (p.descripcion ?? "").ToLower().Contains(s) ||
                    (p.Equipos != null && (
                        ((p.Equipos.nombreArticulo ?? "").ToLower().Contains(s)) ||
                        ((p.Equipos.nombreComercial ?? "").ToLower().Contains(s)) ||
                        ((p.Equipos.codigoArticulo ?? "").ToLower().Contains(s)) ||
                        ((p.Equipos.numIdentificador ?? "").ToLower().Contains(s))
                    )) ||
                    (p.TiposMantenimientos != null && (p.TiposMantenimientos.nombre ?? "").ToLower().Contains(s)) ||
                    (p.Usuarios != null && (
                        ((p.Usuarios.nombre ?? "").ToLower().Contains(s)) ||
                        ((p.Usuarios.apellidoPaterno ?? "").ToLower().Contains(s))
                    )) ||
                    (p.Tareas != null && (p.Tareas.descripcion ?? "").ToLower().Contains(s)) ||
                    (p.Prioridades != null && (p.Prioridades.nombre ?? "").ToLower().Contains(s))
                );
            }

            page = Math.Max(1, page);
            pageSize = Math.Max(1, Math.Min(200, pageSize));

            var total = q.Count();

            // Proyección a DTO para evitar ciclos y controlar lo que devuelves
            var items = q
                .OrderByDescending(p => p.fechaRecordatorio)
                .Skip((page - 1) * pageSize)
                .Take(pageSize)
                .Select(p => new RecordatoriosDto
                {
                    idRecordatorios = p.idRecordatorios,
                    EquiposDto = p.Equipos == null ? null : new EquiposDto
                    {
                        idEquipos = p.Equipos.idEquipos,
                        codigoArticulo = p.Equipos.codigoArticulo,
                        nombreArticulo = p.Equipos.nombreArticulo,
                        nombreComercial = p.Equipos.nombreComercial,
                        numIdentificador = p.Equipos.numIdentificador,
                        descripcion = p.Equipos.descripcion,
                        marca = p.Equipos.marca,
                        modelo = p.Equipos.modelo,
                        fechadeRegistro = p.Equipos.fechadeRegistro,
                        idfUbicaciones = p.Equipos.idfUbicaciones,
                        idfUnidades = p.Equipos.idfUnidades,
                        idfEstatus = p.Equipos.idfEstatus,
                        idfTiposMaquinarias = p.Equipos.idfTiposMaquinarias
                    },
                    TareasDto = p.Tareas == null ? null : new TareasDto
                    {
                        idTareas = p.Tareas.idTareas,
                        idTiposMantenimientos = p.Tareas.idTiposMantenimientos,
                        descripcion = p.Tareas.descripcion
                    },
                    UsuariosDto = p.Usuarios == null ? null : new UsuariosDto
                    {
                        idUsuarios = p.Usuarios.idUsuarios,
                        nombre = p.Usuarios.nombre,
                        apellidoPaterno = p.Usuarios.apellidoPaterno,
                        apellidoMaterno = p.Usuarios.apellidoMaterno,
                    },
                    PrioridadesDto = p.Prioridades == null ? null : new PrioridadesDto
                    {
                        idPrioridades = p.Prioridades.idPrioridades,
                        nombre = p.Prioridades.nombre
                    },
                    TiposMantenimientosDto = p.TiposMantenimientos == null ? null : new TiposMantenimientosDto
                    {
                        idTiposMantenimientos = p.TiposMantenimientos.idTiposMantenimientos,
                        nombre = p.TiposMantenimientos.nombre
                    },
                    recordarS_N = p.recordarS_N,
                    fechaRecordatorio = p.fechaRecordatorio,
                    numeroReporte = p.numeroReporte,
                    descripcion = p.descripcion
                })
                .ToList();

            return Ok(new { total, page, pageSize, items });
        }

        // GET 
        [HttpGet, Route("{id:int}", Name = "GetProgramacion")]
        [ResponseType(typeof(RecordatoriosDto))]
        public IHttpActionResult GetProgramacion(int id)
        {
            var p = _db.Recordatorios
                       .Include(x => x.Equipos)
                       .Include(x => x.Tareas)
                       .Include(x => x.Usuarios)
                       .Include(x => x.Prioridades)
                       .Include(x => x.TiposMantenimientos)
                       .FirstOrDefault(x => x.idRecordatorios == id);

            if (p == null) return NotFound();

            var dto = new RecordatoriosDto
            {
                idRecordatorios = p.idRecordatorios,
                EquiposDto = p.Equipos == null ? null : new EquiposDto
                {
                    idEquipos = p.Equipos.idEquipos,
                    codigoArticulo = p.Equipos.codigoArticulo,
                    nombreArticulo = p.Equipos.nombreArticulo,
                    nombreComercial = p.Equipos.nombreComercial,
                    numIdentificador = p.Equipos.numIdentificador,
                    descripcion = p.Equipos.descripcion,
                    marca = p.Equipos.marca,
                    modelo = p.Equipos.modelo,
                    fechadeRegistro = p.Equipos.fechadeRegistro,
                    idfUbicaciones = p.Equipos.idfUbicaciones,
                    idfUnidades = p.Equipos.idfUnidades,
                    idfEstatus = p.Equipos.idfEstatus,
                    idfTiposMaquinarias = p.Equipos.idfTiposMaquinarias
                },
                TareasDto = p.Tareas == null ? null : new TareasDto
                {
                    idTareas = p.Tareas.idTareas,
                    idTiposMantenimientos = p.Tareas.idTiposMantenimientos,
                    descripcion = p.Tareas.descripcion
                },
                UsuariosDto = p.Usuarios == null ? null : new UsuariosDto
                {
                    idUsuarios = p.Usuarios.idUsuarios,
                    nombre = p.Usuarios.nombre,
                    apellidoPaterno = p.Usuarios.apellidoPaterno,
                    apellidoMaterno = p.Usuarios.apellidoMaterno,
                },
                PrioridadesDto = p.Prioridades == null ? null : new PrioridadesDto
                {
                    idPrioridades = p.Prioridades.idPrioridades,
                    nombre = p.Prioridades.nombre
                },
                TiposMantenimientosDto = p.TiposMantenimientos == null ? null : new TiposMantenimientosDto
                {
                    idTiposMantenimientos = p.TiposMantenimientos.idTiposMantenimientos,
                    nombre = p.TiposMantenimientos.nombre
                },
                recordarS_N = p.recordarS_N,
                fechaRecordatorio = p.fechaRecordatorio,
                numeroReporte = p.numeroReporte,
                descripcion = p.descripcion
            };

            return Ok(dto);
        }

        [HttpGet, Route("NextNumeroReporte/")]
        private IHttpActionResult GetNextNumeroReporte()
        {
            int ultimoRef = _db.Recordatorios.Any() ? _db.Recordatorios.Max(r => (int?)r.numeroReporte) ?? 0 : 0;
            var res = Math.Max(ultimoRef, ultimoRef) + 1;
            return Ok(res);
        }


        // POST create
        [HttpPost, Route("")]
        [ResponseType(typeof(Recordatorios))]
        public IHttpActionResult PostProgramacion(Recordatorios p)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);

            var equipo = _db.Equipos.Find(p.idfEquipos);
            if (equipo == null) return BadRequest("Equipo no encontrado.");

            var tipo = _db.TiposMantenimientos.Find(p.idfTiposMantenimientos);
            if (tipo == null) return BadRequest("Tipo de mantenimiento no encontrado.");

            var dto = new RecordatoriosDto
            {
                idRecordatorios = p.idRecordatorios,
                EquiposDto = p.Equipos == null ? null : new EquiposDto
                {
                    codigoArticulo = p.Equipos.codigoArticulo,
                    nombreArticulo = p.Equipos.nombreArticulo,
                    nombreComercial = p.Equipos.nombreComercial,
                    numIdentificador = p.Equipos.numIdentificador,
                    descripcion = p.Equipos.descripcion,
                    marca = p.Equipos.marca,
                    modelo = p.Equipos.modelo,
                    fechadeRegistro = p.Equipos.fechadeRegistro,
                    idfUbicaciones = p.Equipos.idfUbicaciones,
                    idfUnidades = p.Equipos.idfUnidades,
                    idfEstatus = p.Equipos.idfEstatus,
                    idfTiposMaquinarias = p.Equipos.idfTiposMaquinarias
                },
                TareasDto = p.Tareas == null ? null : new TareasDto
                {
                    idTiposMantenimientos = p.Tareas.idTiposMantenimientos,
                    descripcion = p.Tareas.descripcion
                },
                UsuariosDto = p.Usuarios == null ? null : new UsuariosDto
                {
                    nombre = p.Usuarios.nombre,
                    apellidoPaterno = p.Usuarios.apellidoPaterno,
                    apellidoMaterno = p.Usuarios.apellidoMaterno,
                },
                PrioridadesDto = p.Prioridades == null ? null : new PrioridadesDto
                {
                    nombre = p.Prioridades.nombre
                },
                TiposMantenimientosDto = p.TiposMantenimientos == null ? null : new TiposMantenimientosDto
                {
                    nombre = p.TiposMantenimientos.nombre
                },
                recordarS_N = p.recordarS_N,
                fechaRecordatorio = p.fechaRecordatorio,
                numeroReporte = p.numeroReporte,
                descripcion = p.descripcion
            };

            _db.Recordatorios.Add(p);
            _db.SaveChanges();

            var created = new RecordatoriosDto
            {
                idRecordatorios = p.idRecordatorios,
                EquiposDto = p.Equipos == null ? null : new EquiposDto
                {
                    idEquipos = p.Equipos.idEquipos,
                    codigoArticulo = p.Equipos.codigoArticulo,
                    nombreArticulo = p.Equipos.nombreArticulo,
                    nombreComercial = p.Equipos.nombreComercial,
                    numIdentificador = p.Equipos.numIdentificador,
                    descripcion = p.Equipos.descripcion,
                    marca = p.Equipos.marca,
                    modelo = p.Equipos.modelo,
                    fechadeRegistro = p.Equipos.fechadeRegistro,
                    idfUbicaciones = p.Equipos.idfUbicaciones,
                    idfUnidades = p.Equipos.idfUnidades,
                    idfEstatus = p.Equipos.idfEstatus,
                    idfTiposMaquinarias = p.Equipos.idfTiposMaquinarias
                },
                TareasDto = p.Tareas == null ? null : new TareasDto
                {
                    idTareas = p.Tareas.idTareas,
                    idTiposMantenimientos = p.Tareas.idTiposMantenimientos,
                    descripcion = p.Tareas.descripcion
                },
                UsuariosDto = p.Usuarios == null ? null : new UsuariosDto
                {
                    idUsuarios = p.Usuarios.idUsuarios,
                    nombre = p.Usuarios.nombre,
                    apellidoPaterno = p.Usuarios.apellidoPaterno,
                    apellidoMaterno = p.Usuarios.apellidoMaterno,
                },
                PrioridadesDto = p.Prioridades == null ? null : new PrioridadesDto
                {
                    idPrioridades = p.Prioridades.idPrioridades,
                    nombre = p.Prioridades.nombre
                },
                TiposMantenimientosDto = p.TiposMantenimientos == null ? null : new TiposMantenimientosDto
                {
                    idTiposMantenimientos = p.TiposMantenimientos.idTiposMantenimientos,
                    nombre = p.TiposMantenimientos.nombre
                },
                recordarS_N = p.recordarS_N,
                fechaRecordatorio = p.fechaRecordatorio,
                numeroReporte = p.numeroReporte,
                descripcion = p.descripcion
            };

            return CreatedAtRoute("GetProgramacion", new { id = created.idRecordatorios}, created);
        }



        // PUT update
        [HttpPut, Route("{id:int}")]
        public IHttpActionResult PutProgramacion(int id, RecordatoriosDto dto)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);
            var exist = _db.Recordatorios.Find(id);
            if (exist == null) return NotFound();

            //**idRecordatorios = dto.idRecordatorios

            //EQUIPOS
            exist.Equipos.codigoArticulo = dto.EquiposDto.codigoArticulo;
            exist.Equipos.nombreArticulo = dto.EquiposDto.nombreArticulo;
            exist.Equipos.nombreComercial = dto.EquiposDto.nombreComercial;
            exist.Equipos.numIdentificador = dto.EquiposDto.numIdentificador;
            exist.Equipos.descripcion = dto.EquiposDto.descripcion;
            exist.Equipos.marca = dto.EquiposDto.marca;
            exist.Equipos.modelo = dto.EquiposDto.modelo;
            exist.Equipos.fechadeRegistro = dto.EquiposDto.fechadeRegistro;
            exist.Equipos.idfUbicaciones = dto.EquiposDto.idfUbicaciones;
            exist.Equipos.idfUnidades = dto.EquiposDto.idfUnidades;
            exist.Equipos.idfEstatus = dto.EquiposDto.idfEstatus;
            exist.Equipos.idfTiposMaquinarias = dto.EquiposDto.idfTiposMaquinarias;

            //TAREAS
            exist.Tareas.idTiposMantenimientos = dto.TareasDto.idTiposMantenimientos;
            exist.Tareas.descripcion = dto.TareasDto.descripcion;

            //USUARIOS
            /*exist.Usuarios.nombre = dto.UsuariosDto.nombre;
            exist.Usuarios.apellidoPaterno = dto.UsuariosDto.apellidoPaterno;
            exist.Usuarios.apellidoMaterno = dto.UsuariosDto.apellidoMaterno;*/

            //PRIORIDADES
            exist.Prioridades.nombre = dto.PrioridadesDto.nombre;

            //TIPOSMANTENIMIENTOS
            exist.TiposMantenimientos.nombre = dto.TiposMantenimientosDto.nombre;

            exist.recordarS_N = dto.recordarS_N;
            exist.fechaRecordatorio = dto.fechaRecordatorio;
            exist.numeroReporte = dto.numeroReporte;
            exist.descripcion = dto.descripcion;

            // asignar numero_reporte si tu entidad lo tiene
            //try { exist.numeroReporte = GetNextNumeroReporte(); }
            //catch
            //{ /* si no existe, ignora */}


            _db.SaveChanges();
            return Ok();
        }

        // DELETE
        [HttpDelete, Route("{id:int}")]
        public IHttpActionResult DeleteProgramacion(int id)
        {
            var exist = _db.Recordatorios.Find(id);
            if (exist == null) return NotFound();
            _db.Recordatorios.Remove(exist);
            _db.SaveChanges();
            return Ok();
        }
        protected override void Dispose(bool disposing)
        {
            if (disposing) _db.Dispose();
            base.Dispose(disposing);
        }
    }
}