using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Models
{
	public class EquiposDto
	{
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int idEquipos { get; set; }
        public string codigoArticulo { get; set; }
        public string nombreArticulo { get; set; }
        public string nombreComercial { get; set; }
        public string numIdentificador { get; set; }
        public string descripcion { get; set; }
        public string marca { get; set; }
        public string modelo { get; set; }
        public Nullable<System.DateTime> fechadeRegistro { get; set; }
        public Nullable<int> idfUbicaciones { get; set; }
        public Nullable<int> idfUnidades { get; set; }
        public Nullable<int> idfEstatus { get; set; }
        public Nullable<int> idfTiposMaquinarias { get; set; }

        public EstatusDto EstatusDto { get; set; }
        public TiposMaquinariasDto TiposMaquinariasDto { get; set; }
        public UbicacionesDto UbicacionesDto { get; set; }
        public UnidadesDto UnidadesDto { get; set; }
        public ICollection<LugaresDto> LugaresDto { get; set; }
        public ICollection<QrEquiposDto> QrEquiposDto { get; set; }
        public ICollection<RecordatoriosDto> RecordatoriosDto { get; set; }
        public ICollection<RevisionesDto> RevisionesDto { get; set; }
    }
}