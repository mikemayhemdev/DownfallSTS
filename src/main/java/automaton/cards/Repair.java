package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class Repair extends AbstractBronzeCard {

    public final static String ID = makeID("Repair");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 7;
    private static final int UPG_MAGIC = 3;

    public Repair() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        thisEncodes();
        tags.add(CardTags.HEALING);
       // this.tags.add(SneckoMod.BANNEDFORSNECKO);
        baseBlock = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            atb(new HealAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber));
        }
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}