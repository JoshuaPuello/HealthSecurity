import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { TipoRiesgo } from 'app/shared/model/tipo-riesgo.model';
import { TipoRiesgoService } from './tipo-riesgo.service';
import { TipoRiesgoComponent } from './tipo-riesgo.component';
import { TipoRiesgoDetailComponent } from './tipo-riesgo-detail.component';
import { TipoRiesgoUpdateComponent } from './tipo-riesgo-update.component';
import { TipoRiesgoDeletePopupComponent } from './tipo-riesgo-delete-dialog.component';
import { ITipoRiesgo } from 'app/shared/model/tipo-riesgo.model';

@Injectable({ providedIn: 'root' })
export class TipoRiesgoResolve implements Resolve<ITipoRiesgo> {
    constructor(private service: TipoRiesgoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((tipoRiesgo: HttpResponse<TipoRiesgo>) => tipoRiesgo.body);
        }
        return Observable.of(new TipoRiesgo());
    }
}

export const tipoRiesgoRoute: Routes = [
    {
        path: 'tipo-riesgo',
        component: TipoRiesgoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'healthSecurityApp.tipoRiesgo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-riesgo/:id/view',
        component: TipoRiesgoDetailComponent,
        resolve: {
            tipoRiesgo: TipoRiesgoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.tipoRiesgo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-riesgo/new',
        component: TipoRiesgoUpdateComponent,
        resolve: {
            tipoRiesgo: TipoRiesgoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.tipoRiesgo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-riesgo/:id/edit',
        component: TipoRiesgoUpdateComponent,
        resolve: {
            tipoRiesgo: TipoRiesgoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.tipoRiesgo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoRiesgoPopupRoute: Routes = [
    {
        path: 'tipo-riesgo/:id/delete',
        component: TipoRiesgoDeletePopupComponent,
        resolve: {
            tipoRiesgo: TipoRiesgoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.tipoRiesgo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
