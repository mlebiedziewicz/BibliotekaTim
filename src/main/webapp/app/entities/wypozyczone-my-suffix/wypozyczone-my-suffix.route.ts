import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { WypozyczoneMySuffix } from 'app/shared/model/wypozyczone-my-suffix.model';
import { WypozyczoneMySuffixService } from './wypozyczone-my-suffix.service';
import { WypozyczoneMySuffixComponent } from './wypozyczone-my-suffix.component';
import { WypozyczoneMySuffixDetailComponent } from './wypozyczone-my-suffix-detail.component';
import { WypozyczoneMySuffixUpdateComponent } from './wypozyczone-my-suffix-update.component';
import { WypozyczoneMySuffixDeletePopupComponent } from './wypozyczone-my-suffix-delete-dialog.component';
import { IWypozyczoneMySuffix } from 'app/shared/model/wypozyczone-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class WypozyczoneMySuffixResolve implements Resolve<IWypozyczoneMySuffix> {
    constructor(private service: WypozyczoneMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<WypozyczoneMySuffix> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<WypozyczoneMySuffix>) => response.ok),
                map((wypozyczone: HttpResponse<WypozyczoneMySuffix>) => wypozyczone.body)
            );
        }
        return of(new WypozyczoneMySuffix());
    }
}

export const wypozyczoneRoute: Routes = [
    {
        path: 'wypozyczone-my-suffix',
        component: WypozyczoneMySuffixComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'bibliotekaTimApp.wypozyczone.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wypozyczone-my-suffix/:id/view',
        component: WypozyczoneMySuffixDetailComponent,
        resolve: {
            wypozyczone: WypozyczoneMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.wypozyczone.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wypozyczone-my-suffix/new',
        component: WypozyczoneMySuffixUpdateComponent,
        resolve: {
            wypozyczone: WypozyczoneMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.wypozyczone.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wypozyczone-my-suffix/:id/edit',
        component: WypozyczoneMySuffixUpdateComponent,
        resolve: {
            wypozyczone: WypozyczoneMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.wypozyczone.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wypozyczonePopupRoute: Routes = [
    {
        path: 'wypozyczone-my-suffix/:id/delete',
        component: WypozyczoneMySuffixDeletePopupComponent,
        resolve: {
            wypozyczone: WypozyczoneMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.wypozyczone.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
