/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArtWorkTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ValorationDetailComponent } from '../../../../../../main/webapp/app/entities/valoration/valoration-detail.component';
import { ValorationService } from '../../../../../../main/webapp/app/entities/valoration/valoration.service';
import { Valoration } from '../../../../../../main/webapp/app/entities/valoration/valoration.model';

describe('Component Tests', () => {

    describe('Valoration Management Detail Component', () => {
        let comp: ValorationDetailComponent;
        let fixture: ComponentFixture<ValorationDetailComponent>;
        let service: ValorationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArtWorkTestModule],
                declarations: [ValorationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ValorationService,
                    JhiEventManager
                ]
            }).overrideTemplate(ValorationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ValorationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ValorationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Valoration(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.valoration).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
