using AutoMapper;
using APIMIRAI_Construcciones.Models;
using APIMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace APIMIRAI_Construcciones.Mapping
{
    public class WebProfile : Profile
    {
        public WebProfile()
        {
            CreateMap<CategoriasPreventivasDto, CategoriasPreventivas>()
                .ForMember(dest => dest.DetallesPreventivos, opt => opt.MapFrom(src => src.DetallesPreventivosDto))
                .ReverseMap();

            CreateMap<DetallesPreventivosDto, DetallesPreventivos>()
                .ForMember(dest => dest.CategoriasPreventivas, opt => opt.MapFrom(src => src.CategoriasPreventivasDto))
                .ForMember(dest => dest.Prioridades, opt => opt.MapFrom(src => src.PrioridadesDto))
                .ForMember(dest => dest.Revisiones, opt => opt.MapFrom(src => src.RevisionesDto))
                .ReverseMap();

            CreateMap<EmpresasDto, Empresas>()
                .ForMember(dest => dest.Revisiones, opt => opt.MapFrom(src => src.RevisionesDto))
                .ReverseMap();

            CreateMap<EquiposDto, Equipos>()
                .ForMember(dest => dest.Estatus, opt => opt.MapFrom(src => src.EstatusDto))
                .ForMember(dest => dest.TiposMaquinarias, opt => opt.MapFrom(src => src.TiposMaquinariasDto))
                .ForMember(dest => dest.Ubicaciones, opt => opt.MapFrom(src => src.UbicacionesDto))
                .ForMember(dest => dest.Unidades, opt => opt.MapFrom(src => src.UnidadesDto))
                .ForMember(dest => dest.Lugares, opt => opt.MapFrom(src => src.LugaresDto))
                .ForMember(dest => dest.QrEquipos, opt => opt.MapFrom(src => src.QrEquiposDto))
                .ForMember(dest => dest.Recordatorios, opt => opt.MapFrom(src => src.RecordatoriosDto))
                .ForMember(dest => dest.Revisiones, opt => opt.MapFrom(src => src.RevisionesDto))
                .ReverseMap();

            CreateMap<EstatusDto, Estatus>()
                .ForMember(dest => dest.Equipos, opt => opt.MapFrom(src => src.EquiposDto))
                .ReverseMap();

            CreateMap<LugaresDto, Lugares>()
                .ForMember(dest => dest.Equipos, opt => opt.MapFrom(src => src.EquiposDto))
                .ReverseMap();

            CreateMap<PrioridadesDto, Prioridades>()
                .ForMember(dest => dest.DetallesPreventivos, opt => opt.MapFrom(src => src.DetallesPreventivosDto))
                .ForMember(dest => dest.Recordatorios, opt => opt.MapFrom(src => src.RecordatoriosDto))
                .ForMember(dest => dest.Refacciones, opt => opt.MapFrom(src => src.RefaccionesDto))
                .ReverseMap();

            CreateMap<QrEquiposDto, QrEquipos>()
                .ForMember(dest => dest.Equipos, opt => opt.MapFrom(src => src.EquiposDto))
                .ReverseMap();

            CreateMap<RecordatoriosDto, Recordatorios>()
                .ForMember(dest => dest.Equipos, opt => opt.MapFrom(src => src.EquiposDto))
                .ForMember(dest => dest.Prioridades, opt => opt.MapFrom(src => src.PrioridadesDto))
                .ForMember(dest => dest.Tareas, opt => opt.MapFrom(src => src.TareasDto))
                .ForMember(dest => dest.TiposMantenimientos, opt => opt.MapFrom(src => src.TiposMantenimientosDto))
                .ForMember(dest => dest.Usuarios, opt => opt.MapFrom(src => src.UsuariosDto))
                .ReverseMap();

            CreateMap<RefaccionesDto, Refacciones>()
                .ForMember(dest => dest.Revisiones, opt => opt.MapFrom(src => src.RevisionesDto))
                .ForMember(dest => dest.Unidades, opt => opt.MapFrom(src => src.UnidadesDto))
                .ForMember(dest => dest.Prioridades, opt => opt.MapFrom(src => src.PrioridadesDto))
                .ReverseMap();

            CreateMap<RevisionesDto, Revisiones>()
                .ForMember(dest => dest.DetallesPreventivos, opt => opt.MapFrom(src => src.DetallesPreventivosDto))
                .ForMember(dest => dest.Empresas, opt => opt.MapFrom(src => src.EmpresasDto))
                .ForMember(dest => dest.Equipos, opt => opt.MapFrom(src => src.EquiposDto))
                .ForMember(dest => dest.Refacciones, opt => opt.MapFrom(src => src.RefaccionesDto))
                .ForMember(dest => dest.TiposMantenimientos, opt => opt.MapFrom(src => src.TiposMantenimientosDto))
                .ForMember(dest => dest.Usuarios, opt => opt.MapFrom(src => src.UsuariosDto))
                .ReverseMap();

            CreateMap<RolesDto, Roles>()
                .ForMember(dest => dest.Usuarios, opt => opt.MapFrom(src => src.UsuariosDto))
                .ReverseMap();

            CreateMap<TareasDto, Tareas>()
                .ForMember(dest => dest.Recordatorios, opt => opt.MapFrom(src => src.RecordatoriosDto))
                .ForMember(dest => dest.TiposMantenimientos, opt => opt.MapFrom(src => src.TiposMantenimientosDto))
                .ReverseMap();

            CreateMap<TiposMantenimientosDto, TiposMantenimientos>()
                .ForMember(dest => dest.Recordatorios, opt => opt.MapFrom(src => src.RecordatoriosDto))
                .ForMember(dest => dest.Revisiones, opt => opt.MapFrom(src => src.RevisionesDto))
                .ForMember(dest => dest.Tareas, opt => opt.MapFrom(src => src.TareasDto))
                .ReverseMap();

            CreateMap<TiposMaquinariasDto, TiposMaquinarias>()
                .ForMember(dest => dest.Equipos, opt => opt.MapFrom(src => src.EquiposDto))
                .ReverseMap();

            CreateMap<UbicacionesDto, Ubicaciones>()
                .ForMember(dest => dest.Equipos, opt => opt.MapFrom(src => src.EquiposDto))
                .ReverseMap();

            CreateMap<UnidadesDto, Unidades>()
                .ForMember(dest => dest.Equipos, opt => opt.MapFrom(src => src.EquiposDto))
                .ForMember(dest => dest.Refacciones, opt => opt.MapFrom(src => src.RefaccionesDto))
                .ReverseMap();

            CreateMap<UsuariosDto, Usuarios>()
                .ForMember(dest => dest.Recordatorios, opt => opt.MapFrom(src => src.RecordatoriosDto))
                .ForMember(dest => dest.Revisiones, opt => opt.MapFrom(src => src.RevisionesDto))
                .ForMember(dest => dest.Roles, opt => opt.MapFrom(src => src.RolesDto))
                .ReverseMap();
        }

        //[Obsolete]
        public static void Run()
        {

            AutoMapper.Mapper.Initialize(a =>
            {
                a.AddProfile<WebProfile>();
            });
        }
    }
}