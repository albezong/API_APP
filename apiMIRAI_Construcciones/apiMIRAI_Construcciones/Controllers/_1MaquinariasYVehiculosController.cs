using APIMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Data.Entity;

namespace APIMIRAI_Construcciones.Controllers
{
    [RoutePrefix("api/maquinaria")]
    public class _1MaquinariasYVehiculosController : ApiController
    {
        private readonly PruebaAlmacenTAEPIEntities1 _db = new PruebaAlmacenTAEPIEntities1();

        public class MaquinariaDto
        {
            public int id_equipos { get; set; }
            public string codigo_articulo { get; set; }
            public string nombre_articulo { get; set; }
            public string nombre_comercial { get; set; }
            public string num_identificador { get; set; }
            public string descripcion { get; set; }
            public string marca { get; set; }
            public string modelo { get; set; }
            public DateTime? fechade_registro { get; set; }
            public int? idf_ubicaciones { get; set; }
            public string ubicacion_nombre { get; set; }
            public int? idf_unidades { get; set; }
            public string unidad_nombre { get; set; }
            public int? idf_estatus { get; set; }
            public string estatus_nombre { get; set; }
            public int? idf_tipos_maquinarias { get; set; }
            public string tipo_maquinaria_nombre { get; set; }
            public int? idf_lugares { get; set; }
            public string lugares_nombre { get; set; }
        }

        [HttpGet, Route("")]
        public IHttpActionResult GetMaquinaria(string search = null, string tipo = null, int? tipoId = null, int page = 1, int pageSize = 50)
        {
            var q = _db.Equipos
                .Include(e => e.TiposMaquinarias)
                .Include(e => e.Estatus)
                .Include(e => e.Unidades)
                .Include(e => e.Ubicaciones)
                .AsQueryable();

            if (tipoId.HasValue)
            {
                q = q.Where(e => e.idfTiposMaquinarias == tipoId.Value);
            }
            else if (!string.IsNullOrEmpty(tipo))
            {
                
                string t = tipo.Trim().ToLower();

                
                if (t == "ligera" || t == "pesada" || t == "unitario")
                {
                    q = q.Where(e => e.TiposMaquinarias != null && e.TiposMaquinarias.nombre.ToLower().Contains(t));
                }
                else
                {
                   
                    q = q.Where(e => e.TiposMaquinarias != null && e.TiposMaquinarias.nombre.ToLower().Contains(t));
                }
            }

            if (!string.IsNullOrEmpty(search))
            {
                string s = search.Trim().ToLower();
                q = q.Where(e =>
                    (e.codigoArticulo ?? "").ToLower().Contains(s) ||
                    (e.nombreArticulo ?? "").ToLower().Contains(s) ||
                    (e.nombreComercial ?? "").ToLower().Contains(s) ||
                    (e.numIdentificador ?? "").ToLower().Contains(s) ||
                    (e.marca ?? "").ToLower().Contains(s) ||
                    (e.modelo ?? "").ToLower().Contains(s) ||
                    (e.Lugares!= null && e.Lugares.FirstOrDefault().nombreLugar.ToLower().Contains(s)) ||
                    (e.TiposMaquinarias != null && e.TiposMaquinarias.nombre.ToLower().Contains(s)) ||
                    (e.Unidades != null && e.Unidades.nombre.ToLower().Contains(s)) ||
                    (e.Ubicaciones != null && e.Ubicaciones.nombre.ToLower().Contains(s))
                );
            }

            page = Math.Max(1, page);
            pageSize = Math.Max(1, Math.Min(200, pageSize)); 

            var total = q.Count();

            var items = q
                .OrderByDescending(e => e.fechadeRegistro)
                .Skip((page - 1) * pageSize)
                .Take(pageSize)
                .Select(e => new MaquinariaDto
                {
                    id_equipos = e.idEquipos,
                    codigo_articulo = e.codigoArticulo,
                    nombre_articulo = e.nombreArticulo,
                    nombre_comercial = e.nombreComercial,
                    num_identificador = e.numIdentificador,
                    descripcion = e.descripcion,
                    marca = e.marca,
                    modelo = e.modelo,
                    fechade_registro = e.fechadeRegistro,
                    idf_ubicaciones = e.idfUbicaciones,
                    ubicacion_nombre = e.Ubicaciones != null ? e.Ubicaciones.nombre : null,
                    idf_unidades = e.idfUnidades,
                    unidad_nombre = e.Unidades != null ? e.Unidades.nombre : null,
                    idf_estatus = e.idfEstatus,
                    estatus_nombre = e.Estatus != null ? e.Estatus.nombre : null,
                    idf_tipos_maquinarias = e.idfTiposMaquinarias,
                    tipo_maquinaria_nombre = e.TiposMaquinarias != null ? e.TiposMaquinarias.nombre : null
                })
                .ToList();

            var result = new
            {
                total,
                page,
                pageSize,
                items
            };

            return Ok(result);
        }

        [HttpGet, Route("{id:int}")]
        public IHttpActionResult GetMaquinariaById(int id)
        {
            var e = _db.Equipos
                .Include(x => x.TiposMaquinarias)
                .Include(x => x.Estatus)
                .Include(x => x.Unidades)
                .Include(x => x.Ubicaciones)
                .Include(x => x.Lugares)
                .FirstOrDefault(x => x.idEquipos == id);

            if (e == null) return NotFound();

            var dto = new MaquinariaDto
            {
                id_equipos = e.idEquipos,
                codigo_articulo = e.codigoArticulo,
                nombre_articulo = e.nombreArticulo,
                nombre_comercial = e.nombreComercial,
                num_identificador = e.numIdentificador,
                descripcion = e.descripcion,
                marca = e.marca,
                modelo = e.modelo,
                fechade_registro = e.fechadeRegistro,
                idf_ubicaciones = e.idfUbicaciones,
                ubicacion_nombre = e.Ubicaciones != null ? e.Ubicaciones.nombre : null,
                idf_unidades = e.idfUnidades,
                unidad_nombre = e.Unidades != null ? e.Unidades.nombre : null,
                idf_estatus = e.idfEstatus,
                estatus_nombre = e.Estatus != null ? e.Estatus.nombre : null,
                idf_tipos_maquinarias = e.idfTiposMaquinarias,
                tipo_maquinaria_nombre = e.TiposMaquinarias != null ? e.TiposMaquinarias.nombre : null,
                /*idf_lugares= e.Lugares.FirstOrDefault().idLugares,
                lugares_nombre = e.Lugares != null ? e.Lugares.FirstOrDefault().nombreLugar : null,*/
            };

            return Ok(dto);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing) _db.Dispose();
            base.Dispose(disposing);
        }
    }
}