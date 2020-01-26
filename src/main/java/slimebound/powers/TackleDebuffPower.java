package slimebound.powers;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.vfx.FakeFlashAtkImgEffect;
import slimebound.vfx.SlimeDripsEffectPurple;


public class TackleDebuffPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:TackleDebuffPower";
    public static final String NAME = "TackleDebuffPower";
    public static PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final String IMG = "powers/TackleDebuff.png";
    public boolean doubleUp = false;
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static String[] DESCRIPTIONS;
    private AbstractCreature source;
    public boolean triggered = false;


    public TackleDebuffPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.name = NAME;

        this.ID = POWER_ID;
        this.amount = amount;

        this.owner = owner;

        this.source = source;


        this.img = new com.badlogic.gdx.graphics.Texture(SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();


    }


    public void updateDescription() {

        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);


    }






}





