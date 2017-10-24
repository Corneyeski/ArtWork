import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Album } from './album.model';
import { AlbumService } from './album.service';

@Component({
    selector: 'jhi-album-detail',
    templateUrl: './album-detail.component.html'
})
export class AlbumDetailComponent implements OnInit, OnDestroy {

    album: Album;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private albumService: AlbumService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAlbums();
    }

    load(id) {
        this.albumService.find(id).subscribe((album) => {
            this.album = album;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAlbums() {
        this.eventSubscriber = this.eventManager.subscribe(
            'albumListModification',
            (response) => this.load(this.album.id)
        );
    }
}
