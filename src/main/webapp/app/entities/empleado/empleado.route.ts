import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Empleado } from 'app/shared/model/empleado.model';
import { EmpleadoService } from './empleado.service';
import { EmpleadoComponent } from './empleado.component';
import { EmpleadoDetailComponent } from './empleado-detail.component';
import { EmpleadoUpdateComponent } from './empleado-update.component';
import { EmpleadoDeletePopupComponent } from './empleado-delete-dialog.component';
import { IEmpleado } from 'app/shared/model/empleado.model';

@Injectable({ providedIn: 'root' })
export class EmpleadoResolve implements Resolve<IEmpleado> {
    constructor(private service: EmpleadoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((empleado: HttpResponse<Empleado>) => empleado.body);
        }
        return Observable.of(new Empleado());
    }
}

export const empleadoRoute: Routes = [
    {
        path: 'empleado',
        component: EmpleadoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'healthSecurityWebApp.empleado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'empleado/:id/view',
        component: EmpleadoDetailComponent,
        resolve: {
            empleado: EmpleadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityWebApp.empleado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'empleado/new',
        component: EmpleadoUpdateComponent,
        resolve: {
            empleado: EmpleadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityWebApp.empleado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'empleado/:id/edit',
        component: EmpleadoUpdateComponent,
        resolve: {
            empleado: EmpleadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityWebApp.empleado.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const empleadoPopupRoute: Routes = [
    {
        path: 'empleado/:id/delete',
        component: EmpleadoDeletePopupComponent,
        resolve: {
            empleado: EmpleadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityWebApp.empleado.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
