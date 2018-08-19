import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Tema } from 'app/shared/model/tema.model';
import { TemaService } from './tema.service';
import { TemaComponent } from './tema.component';
import { TemaDetailComponent } from './tema-detail.component';
import { TemaUpdateComponent } from './tema-update.component';
import { TemaDeletePopupComponent } from './tema-delete-dialog.component';
import { ITema } from 'app/shared/model/tema.model';

@Injectable({ providedIn: 'root' })
export class TemaResolve implements Resolve<ITema> {
    constructor(private service: TemaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((tema: HttpResponse<Tema>) => tema.body);
        }
        return Observable.of(new Tema());
    }
}

export const temaRoute: Routes = [
    {
        path: 'tema',
        component: TemaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'healthSecurityApp.tema.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tema/:id/view',
        component: TemaDetailComponent,
        resolve: {
            tema: TemaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.tema.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tema/new',
        component: TemaUpdateComponent,
        resolve: {
            tema: TemaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.tema.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tema/:id/edit',
        component: TemaUpdateComponent,
        resolve: {
            tema: TemaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.tema.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const temaPopupRoute: Routes = [
    {
        path: 'tema/:id/delete',
        component: TemaDeletePopupComponent,
        resolve: {
            tema: TemaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.tema.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
