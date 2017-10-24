/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArtWorkTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AlbumDetailComponent } from '../../../../../../main/webapp/app/entities/album/album-detail.component';
import { AlbumService } from '../../../../../../main/webapp/app/entities/album/album.service';
import { Album } from '../../../../../../main/webapp/app/entities/album/album.model';

describe('Component Tests', () => {

    describe('Album Management Detail Component', () => {
        let comp: AlbumDetailComponent;
        let fixture: ComponentFixture<AlbumDetailComponent>;
        let service: AlbumService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArtWorkTestModule],
                declarations: [AlbumDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AlbumService,
                    JhiEventManager
                ]
            }).overrideTemplate(AlbumDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AlbumDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AlbumService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Album(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.album).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
