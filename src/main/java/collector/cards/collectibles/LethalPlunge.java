package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.cards.SentryWave;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class LethalPlunge extends AbstractCollectibleCard {
    public final static String ID = makeID(LethalPlunge.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 20, 4, , , 3, -1

    private boolean noHover = false;
    public LethalPlunge(boolean noHover) {
        //TODO - does this need to be a Colorless, not a collectible?
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 20;
        isEthereal = true;
        exhaust = true;
        baseMagicNumber = magicNumber = 3;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.noHover = noHover;
        if (!this.noHover) cardsToPreview = new DaggerCard(true);
    }


    public LethalPlunge(){
        this(false);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new LoseHPAction(p, p, magicNumber));
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        AbstractCard c = new DaggerCard();
        if (upgraded) c.upgrade();
        atb(new MakeTempCardInDrawPileAction(c, 1, true, true));


    }

    public void upp() {
        upgradeDamage(5);
        cardsToPreview.upgrade();
    }
}