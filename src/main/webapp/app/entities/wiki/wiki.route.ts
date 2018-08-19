import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Wiki } from 'app/shared/model/wiki.model';
import { WikiService } from './wiki.service';
import { WikiComponent } from './wiki.component';
import { WikiDetailComponent } from './wiki-detail.component';
import { WikiUpdateComponent } from './wiki-update.component';
import { WikiDeletePopupComponent } from './wiki-delete-dialog.component';
import { IWiki } from 'app/shared/model/wiki.model';

@Injectable({ providedIn: 'root' })
export class WikiResolve implements Resolve<IWiki> {
    constructor(private service: WikiService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((wiki: HttpResponse<Wiki>) => wiki.body);
        }
        return Observable.of(new Wiki());
    }
}

export const wikiRoute: Routes = [
    {
        path: 'wiki',
        component: WikiComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'healthSecurityApp.wiki.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wiki/:id/view',
        component: WikiDetailComponent,
        resolve: {
            wiki: WikiResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.wiki.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wiki/new',
        component: WikiUpdateComponent,
        resolve: {
            wiki: WikiResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.wiki.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wiki/:id/edit',
        component: WikiUpdateComponent,
        resolve: {
            wiki: WikiResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.wiki.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wikiPopupRoute: Routes = [
    {
        path: 'wiki/:id/delete',
        component: WikiDeletePopupComponent,
        resolve: {
            wiki: WikiResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'healthSecurityApp.wiki.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
