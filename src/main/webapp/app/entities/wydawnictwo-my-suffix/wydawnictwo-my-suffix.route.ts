import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { WydawnictwoMySuffix } from 'app/shared/model/wydawnictwo-my-suffix.model';
import { WydawnictwoMySuffixService } from './wydawnictwo-my-suffix.service';
import { WydawnictwoMySuffixComponent } from './wydawnictwo-my-suffix.component';
import { WydawnictwoMySuffixDetailComponent } from './wydawnictwo-my-suffix-detail.component';
import { WydawnictwoMySuffixUpdateComponent } from './wydawnictwo-my-suffix-update.component';
import { WydawnictwoMySuffixDeletePopupComponent } from './wydawnictwo-my-suffix-delete-dialog.component';
import { IWydawnictwoMySuffix } from 'app/shared/model/wydawnictwo-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class WydawnictwoMySuffixResolve implements Resolve<IWydawnictwoMySuffix> {
    constructor(private service: WydawnictwoMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<WydawnictwoMySuffix> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<WydawnictwoMySuffix>) => response.ok),
                map((wydawnictwo: HttpResponse<WydawnictwoMySuffix>) => wydawnictwo.body)
            );
        }
        return of(new WydawnictwoMySuffix());
    }
}

export const wydawnictwoRoute: Routes = [
    {
        path: 'wydawnictwo-my-suffix',
        component: WydawnictwoMySuffixComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'bibliotekaTimApp.wydawnictwo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wydawnictwo-my-suffix/:id/view',
        component: WydawnictwoMySuffixDetailComponent,
        resolve: {
            wydawnictwo: WydawnictwoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.wydawnictwo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wydawnictwo-my-suffix/new',
        component: WydawnictwoMySuffixUpdateComponent,
        resolve: {
            wydawnictwo: WydawnictwoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.wydawnictwo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wydawnictwo-my-suffix/:id/edit',
        component: WydawnictwoMySuffixUpdateComponent,
        resolve: {
            wydawnictwo: WydawnictwoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.wydawnictwo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wydawnictwoPopupRoute: Routes = [
    {
        path: 'wydawnictwo-my-suffix/:id/delete',
        component: WydawnictwoMySuffixDeletePopupComponent,
        resolve: {
            wydawnictwo: WydawnictwoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.wydawnictwo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
