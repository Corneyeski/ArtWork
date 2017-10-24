/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArtWorkTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BlockedDetailComponent } from '../../../../../../main/webapp/app/entities/blocked/blocked-detail.component';
import { BlockedService } from '../../../../../../main/webapp/app/entities/blocked/blocked.service';
import { Blocked } from '../../../../../../main/webapp/app/entities/blocked/blocked.model';

describe('Component Tests', () => {

    describe('Blocked Management Detail Component', () => {
        let comp: BlockedDetailComponent;
        let fixture: ComponentFixture<BlockedDetailComponent>;
        let service: BlockedService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArtWorkTestModule],
                declarations: [BlockedDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BlockedService,
                    JhiEventManager
                ]
            }).overrideTemplate(BlockedDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BlockedDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BlockedService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Blocked(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.blocked).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
