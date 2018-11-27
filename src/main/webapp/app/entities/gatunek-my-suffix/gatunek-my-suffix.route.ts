import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { GatunekMySuffix } from 'app/shared/model/gatunek-my-suffix.model';
import { GatunekMySuffixService } from './gatunek-my-suffix.service';
import { GatunekMySuffixComponent } from './gatunek-my-suffix.component';
import { GatunekMySuffixDetailComponent } from './gatunek-my-suffix-detail.component';
import { GatunekMySuffixUpdateComponent } from './gatunek-my-suffix-update.component';
import { GatunekMySuffixDeletePopupComponent } from './gatunek-my-suffix-delete-dialog.component';
import { IGatunekMySuffix } from 'app/shared/model/gatunek-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class GatunekMySuffixResolve implements Resolve<IGatunekMySuffix> {
    constructor(private service: GatunekMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<GatunekMySuffix> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<GatunekMySuffix>) => response.ok),
                map((gatunek: HttpResponse<GatunekMySuffix>) => gatunek.body)
            );
        }
        return of(new GatunekMySuffix());
    }
}

export const gatunekRoute: Routes = [
    {
        path: 'gatunek-my-suffix',
        component: GatunekMySuffixComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'bibliotekaTimApp.gatunek.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gatunek-my-suffix/:id/view',
        component: GatunekMySuffixDetailComponent,
        resolve: {
            gatunek: GatunekMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.gatunek.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gatunek-my-suffix/new',
        component: GatunekMySuffixUpdateComponent,
        resolve: {
            gatunek: GatunekMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.gatunek.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gatunek-my-suffix/:id/edit',
        component: GatunekMySuffixUpdateComponent,
        resolve: {
            gatunek: GatunekMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.gatunek.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gatunekPopupRoute: Routes = [
    {
        path: 'gatunek-my-suffix/:id/delete',
        component: GatunekMySuffixDeletePopupComponent,
        resolve: {
            gatunek: GatunekMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.gatunek.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
