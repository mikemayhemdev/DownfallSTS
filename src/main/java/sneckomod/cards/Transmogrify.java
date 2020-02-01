package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class Transmogrify extends AbstractSneckoCard {

    public final static String ID = makeID("Transmogrify");

    //stupid intellij stuff SKILL, SELF, RARE

    public Transmogrify() {
        super(ID, "Beta",1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractRelic> eligibleRelicsList = new ArrayList<>(AbstractDungeon.player.relics);
        if (!eligibleRelicsList.isEmpty()) {
            AbstractRelic q = eligibleRelicsList.get(AbstractDungeon.cardRandomRng.random(eligibleRelicsList.size() - 1));
            q.flash();
            AbstractDungeon.player.loseRelic(q.relicId);
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH/2F, Settings.HEIGHT/2F, AbstractDungeon.returnRandomRelic(q.tier));
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}