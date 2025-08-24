using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using Newtonsoft.Json;

namespace APIMIRAI_Construcciones.Models
{
    public class RolesDto
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int idRoles { get; set; }
        public string nombre { get; set; }

        [JsonIgnore]
        public ICollection<UsuariosDto> UsuariosDto { get; set; }
    }
}