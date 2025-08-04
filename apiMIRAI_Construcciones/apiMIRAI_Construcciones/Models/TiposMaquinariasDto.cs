using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Models
{
	public class TiposMaquinariasDto
	{
        public int idTiposMaquinarias { get; set; }
        public string nombre { get; set; }

        public ICollection<EquiposDto> EquiposDto { get; set; }
    }
}