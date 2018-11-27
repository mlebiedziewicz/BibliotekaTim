/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BibliotekaTimTestModule } from '../../../test.module';
import { KsiazkaMySuffixUpdateComponent } from 'app/entities/ksiazka-my-suffix/ksiazka-my-suffix-update.component';
import { KsiazkaMySuffixService } from 'app/entities/ksiazka-my-suffix/ksiazka-my-suffix.service';
import { KsiazkaMySuffix } from 'app/shared/model/ksiazka-my-suffix.model';

describe('Component Tests', () => {
    describe('KsiazkaMySuffix Management Update Component', () => {
        let comp: KsiazkaMySuffixUpdateComponent;
        let fixture: ComponentFixture<KsiazkaMySuffixUpdateComponent>;
        let service: KsiazkaMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BibliotekaTimTestModule],
                declarations: [KsiazkaMySuffixUpdateComponent]
            })
                .overrideTemplate(KsiazkaMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(KsiazkaMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KsiazkaMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new KsiazkaMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ksiazka = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new KsiazkaMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ksiazka = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
