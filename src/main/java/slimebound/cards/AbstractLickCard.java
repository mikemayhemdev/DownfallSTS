package slimebound.cards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLickCard extends AbstractSlimeboundCard {

    public AbstractLickCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        if (Settings.language == Settings.GameLanguage.RUS) {
            tags.add(BaseMod.getKeywordTitle("slimeboundmod:lick"));
        }
        return tags;
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        List<TooltipInfo> tips = new ArrayList<>();
        if (Settings.language == Settings.GameLanguage.RUS) {
            tips.add(new TooltipInfo(BaseMod.getKeywordTitle("slimeboundmod:lick"), BaseMod.getKeywordDescription("slimeboundmod:lick")));
        }
        return tips;
    }
}
