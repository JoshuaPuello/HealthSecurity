import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Respuesta } from 'app/shared/model/respuesta.model';
import { RespuestaService } from './respuesta.service';
import { RespuestaComponent } from './respuesta.component';
import { RespuestaDetailComponent } from './respuesta-detail.component';
import { RespuestaUpdateComponent } from './respuesta-update.component';
import { RespuestaDeletePopupComponent } from './respuesta-delete-dialog.component';
import { IRespuesta } from 'app/shared/model/respuesta.model';

@Injectable({ providedIn: 'root' })
export class RespuestaResolve implements Resolve<IRespuesta> {
    constructor(private service: RespuestaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((respuesta: HttpResponse<Respuesta>) => respuesta.body);
        }
        return Observable.of(new Respuesta());
    }
}

export const respuestaRoute: Routes = [
    {
        path: 'respuesta',
        component: RespuestaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'healthSecurityApp.respuesta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'respuesta/:id/view',
        component: RespuestaDetailComponent,
        resolve: {
            respuesta: RespuestaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.respuesta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'respuesta/new',
        component: RespuestaUpdateComponent,
        resolve: {
            respuesta: RespuestaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.respuesta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'respuesta/:id/edit',
        component: RespuestaUpdateComponent,
        resolve: {
            respuesta: RespuestaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.respuesta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const respuestaPopupRoute: Routes = [
    {
        path: 'respuesta/:id/delete',
        component: RespuestaDeletePopupComponent,
        resolve: {
            respuesta: RespuestaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.respuesta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
