import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { AreaRiesgo } from 'app/shared/model/area-riesgo.model';
import { AreaRiesgoService } from './area-riesgo.service';
import { AreaRiesgoComponent } from './area-riesgo.component';
import { AreaRiesgoDetailComponent } from './area-riesgo-detail.component';
import { AreaRiesgoUpdateComponent } from './area-riesgo-update.component';
import { AreaRiesgoDeletePopupComponent } from './area-riesgo-delete-dialog.component';
import { IAreaRiesgo } from 'app/shared/model/area-riesgo.model';

@Injectable({ providedIn: 'root' })
export class AreaRiesgoResolve implements Resolve<IAreaRiesgo> {
    constructor(private service: AreaRiesgoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((areaRiesgo: HttpResponse<AreaRiesgo>) => areaRiesgo.body);
        }
        return Observable.of(new AreaRiesgo());
    }
}

export const areaRiesgoRoute: Routes = [
    {
        path: 'area-riesgo',
        component: AreaRiesgoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'healthSecurityWebApp.areaRiesgo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'area-riesgo/:id/view',
        component: AreaRiesgoDetailComponent,
        resolve: {
            areaRiesgo: AreaRiesgoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityWebApp.areaRiesgo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'area-riesgo/new',
        component: AreaRiesgoUpdateComponent,
        resolve: {
            areaRiesgo: AreaRiesgoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityWebApp.areaRiesgo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'area-riesgo/:id/edit',
        component: AreaRiesgoUpdateComponent,
        resolve: {
            areaRiesgo: AreaRiesgoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityWebApp.areaRiesgo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const areaRiesgoPopupRoute: Routes = [
    {
        path: 'area-riesgo/:id/delete',
        component: AreaRiesgoDeletePopupComponent,
        resolve: {
            areaRiesgo: AreaRiesgoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityWebApp.areaRiesgo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
