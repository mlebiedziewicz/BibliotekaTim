/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BibliotekaTimTestModule } from '../../../test.module';
import { WypozyczoneMySuffixUpdateComponent } from 'app/entities/wypozyczone-my-suffix/wypozyczone-my-suffix-update.component';
import { WypozyczoneMySuffixService } from 'app/entities/wypozyczone-my-suffix/wypozyczone-my-suffix.service';
import { WypozyczoneMySuffix } from 'app/shared/model/wypozyczone-my-suffix.model';

describe('Component Tests', () => {
    describe('WypozyczoneMySuffix Management Update Component', () => {
        let comp: WypozyczoneMySuffixUpdateComponent;
        let fixture: ComponentFixture<WypozyczoneMySuffixUpdateComponent>;
        let service: WypozyczoneMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BibliotekaTimTestModule],
                declarations: [WypozyczoneMySuffixUpdateComponent]
            })
                .overrideTemplate(WypozyczoneMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WypozyczoneMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WypozyczoneMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new WypozyczoneMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.wypozyczone = entity;
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
                    const entity = new WypozyczoneMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.wypozyczone = entity;
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
