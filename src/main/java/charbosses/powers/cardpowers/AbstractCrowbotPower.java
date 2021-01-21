package charbosses.powers.cardpowers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstractCrowbotPower extends AbstractPower {
    private TextureAtlas powerAtlas;

    public AbstractCrowbotPower(String id) {
        this.powerAtlas = null;


        this.ID = id;
        this.powerStrings = CardCrawlGame.languagePack.getPowerStrings(this.ID);
        this.name = this.NAME = this.powerStrings.NAME;
        this.DESCRIPTIONS = this.powerStrings.DESCRIPTIONS;
    }

    protected final PowerStrings powerStrings;
    protected final String NAME;
    protected final String[] DESCRIPTIONS;

    protected void loadRegion(String fileName) {
        if (this.powerAtlas != null) {
            this.region48 = this.powerAtlas.findRegion("48/" + fileName);
            this.region128 = this.powerAtlas.findRegion("128/" + fileName);
        }

        if (this.region48 == null && this.region128 == null)
            super.loadRegion(fileName);
    }
}


