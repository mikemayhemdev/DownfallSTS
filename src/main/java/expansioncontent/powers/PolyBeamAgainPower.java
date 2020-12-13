package expansioncontent.powers;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import expansioncontent.cards.PolyBeam;
import expansioncontent.expansionContentMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;


public class PolyBeamAgainPower extends AbstractPower {
    public static final String POWER_ID = expansionContentMod.makeID("PolyBeamAgainPower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;
    public static final String IMG = "powers/polybeampower.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.BUFF;
    private AbstractCreature source;
    private AbstractCard card;


    public PolyBeamAgainPower(AbstractCreature owner, AbstractCreature source, int amount, AbstractCard card) {

        this.name = NAME;

        this.ID = POWER_ID;


        this.owner = owner;
        this.card = card;

        this.source = source;


        this.img = new com.badlogic.gdx.graphics.Texture(SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        this.amount = amount;

        updateDescription();

    }


    public void updateDescription() {


        if (this.amount <= 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = (DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
        }


    }


    public void atStartOfTurn() {

        flash();

        AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(true);


        AbstractCard tmp = card.makeStatEquivalentCopy();
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        tmp.target_x = (Settings.WIDTH / 2.0F - 300.0F * Settings.scale);
        tmp.target_y = (Settings.HEIGHT / 2.0F);
        tmp.freeToPlayOnce = true;

        if (m != null) {
            tmp.calculateCardDamage(m);
        }

        tmp.purgeOnUse = true;
        PolyBeam tmpPB = (PolyBeam) tmp;
        tmpPB.tags.remove(expansionContentMod.STUDY_SHAPES);
        tmpPB.tags.remove(expansionContentMod.STUDY);
        tmpPB.isACopy = true;
        tmpPB.rawDescription = tmpPB.UPGRADE_DESCRIPTION;
        tmpPB.initializeDescription();
        AbstractDungeon.actionManager.cardQueue.add(new com.megacrit.cardcrawl.cards.CardQueueItem(tmpPB, m, card.energyOnUse));


        if (this.amount <= 1) {

            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner, this.owner, PolyBeamAgainPower.POWER_ID));

        } else {

            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ReducePowerAction(this.owner, this.owner, PolyBeamAgainPower.POWER_ID, 1));

        }

    }


}



