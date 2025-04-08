package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.MegaSpeechBubble;
import expansioncontent.expansionContentMod;
import slimebound.cards.SlimeCrush;
import slimebound.powers.EnergizedSlimeboundPower;
import slimebound.powers.NextTurnGainSlimeCrush;
import slimebound.powers.NextTurnGainStrengthPower;

import static expansioncontent.expansionContentMod.loadJokeCardImage;

public class SuperPrepareCrush extends AbstractExpansionCard {
    public final static String ID = makeID("SuperPrepareCrush");


    private static final int BLOCK = 10;
    private static final int UPGRADE_BLOCK = 5;
    private static final int MAGIC = 3;

    public SuperPrepareCrush() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_slime.png", "expansioncontentResources/images/1024/bg_boss_slime.png");

        tags.add(expansionContentMod.STUDY_SLIMEBOSS);
        tags.add(expansionContentMod.STUDY);
        this.exhaust = true;
        this.cardsToPreview = new SlimeCrush();
        this.magicNumber = this.baseMagicNumber = 3;
        cardsToPreview = new SlimeCrush();
        loadJokeCardImage(this, "SuperPrepareCrush.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new MegaSpeechBubble(p.hb.cX, p.hb.cY, 1.0F, EXTENDED_DESCRIPTION[0], true));
        AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(0.3F, ScreenShake.ShakeDur.MED, ScreenShake.ShakeIntensity.LOW));

        if (upgraded)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnergizedSlimeboundPower(p, p, 4), 4));
        else
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnergizedSlimeboundPower(p, p, 3), 3));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnGainStrengthPower(p, p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnGainSlimeCrush(p, p, 1, this.upgraded), 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            AbstractCard q = new SlimeCrush();
            q.upgrade();
            cardsToPreview = q;
            this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_slime.png", "expansioncontentResources/images/1024/bg_boss_slime.png");
            upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}


