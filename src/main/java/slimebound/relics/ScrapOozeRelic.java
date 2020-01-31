package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import slimebound.actions.SlimeSpawnAction;
import slimebound.characters.SlimeboundCharacter;

public class ScrapOozeRelic extends CustomRelic {
    public static final String ID = "Slimebound:ScrapOozeRelic";
    public static final String IMG_PATH = "relics/scrapOoze.png";
    public static final String OUTLINE_IMG_PATH = "relics/scrapOozeOutline.png";
    private static final int HP_PER_CARD = 1;
    public int scrapAmount = 0;

    public ScrapOozeRelic() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.SPECIAL, LandingSound.CLINK);
        if (this.counter <= 6) this.counter = 6;
        this.description = this.getUpdatedDescription();
        this.tips.remove(0);
        this.tips.add(new PowerTip(this.name, this.description));

    }

    public void updateDescription(AbstractPlayer.PlayerClass c) {
        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public String getUpdatedDescription() {
        return (this.DESCRIPTIONS[0]);
    }

    public void atBattleStartPreDraw() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.ScrapOozeSlime(), false, false));

    }

    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlimeboundCharacter;
    }

    public void incrementScrapNum(int amount) {
        if (amount != 0) {
            this.counter += amount;

            this.tips.clear();
            this.description = this.getUpdatedDescription();
            this.tips.add(new PowerTip(this.name, this.description));
            this.flash();
        }
    }


    @Override
    public AbstractRelic makeCopy() {
        return new ScrapOozeRelic();
    }

}