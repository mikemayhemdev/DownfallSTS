package expansioncontent.cards.deprecated;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.vfx.MegaSpeechBubble;
import downfall.util.CardIgnore;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;

@CardIgnore
public class CaCaw extends AbstractExpansionCard {
    public final static String ID = makeID("CaCaw");

    private static final int MAGIC = 1;

    public CaCaw() {
        super(ID, 3, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);

        tags.add(expansionContentMod.STUDY_AWAKENEDONE);
        tags.add(expansionContentMod.STUDY);

        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playA("VO_CULTIST_1A", -.1f);

        AbstractDungeon.effectList.add(new MegaSpeechBubble(p.hb.cX, p.hb.cY, 1.0F, Byrd.DIALOG[0], true));

        atb(new ApplyPowerAction(p, p, new RitualPower(p, 1, true), 1));

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }

}


