using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Models
{
	public class CategoriasPreventivasDto
	{
        public int idCategoriasPreventivas { get; set; }
        public string nombreCategorias { get; set; }

        public ICollection<DetallesPreventivosDto> DetallesPreventivosDto { get; set; }
    }
}