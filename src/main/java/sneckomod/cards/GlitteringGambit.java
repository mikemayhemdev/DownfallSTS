package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.ChangeGoldAction;

public class GlitteringGambit extends AbstractSneckoCard {

    public final static String ID = makeID("GlitteringGambit");

    //stupid intellij stuff SKILL, SELF, RARE

    private static final int MAGIC = 85;
    private static final int UPG_MAGIC = 5;

    public GlitteringGambit() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(SneckoMod.SNEKPROOF);
        exhaust = true;
        tags.add(CardTags.HEALING);
        tags.add(SneckoMod.RNG);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int x = getRandomNum(magicNumber, 120, this);
        int y = AbstractDungeon.player.gold * x;
        int z = y / 100;
        atb(new ChangeGoldAction(z - x));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}