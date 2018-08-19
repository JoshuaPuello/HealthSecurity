import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Reporte } from 'app/shared/model/reporte.model';
import { ReporteService } from './reporte.service';
import { ReporteComponent } from './reporte.component';
import { ReporteDetailComponent } from './reporte-detail.component';
import { ReporteUpdateComponent } from './reporte-update.component';
import { ReporteDeletePopupComponent } from './reporte-delete-dialog.component';
import { IReporte } from 'app/shared/model/reporte.model';

@Injectable({ providedIn: 'root' })
export class ReporteResolve implements Resolve<IReporte> {
    constructor(private service: ReporteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((reporte: HttpResponse<Reporte>) => reporte.body);
        }
        return Observable.of(new Reporte());
    }
}

export const reporteRoute: Routes = [
    {
        path: 'reporte',
        component: ReporteComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'healthSecurityApp.reporte.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reporte/:id/view',
        component: ReporteDetailComponent,
        resolve: {
            reporte: ReporteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.reporte.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reporte/new',
        component: ReporteUpdateComponent,
        resolve: {
            reporte: ReporteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.reporte.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reporte/:id/edit',
        component: ReporteUpdateComponent,
        resolve: {
            reporte: ReporteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.reporte.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reportePopupRoute: Routes = [
    {
        path: 'reporte/:id/delete',
        component: ReporteDeletePopupComponent,
        resolve: {
            reporte: ReporteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.reporte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
